package com.google.devtools.moe.client.codebase;

import com.google.devtools.moe.client.Ui;
import com.google.devtools.moe.client.codebase.expressions.EditExpression;
import com.google.devtools.moe.client.project.ProjectContext;
import com.google.devtools.moe.client.translation.editors.Editor;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Peforms the codebase transformation for an EditExpression. */
@Singleton
public class EditedCodebaseProcessor implements CodebaseProcessor<EditExpression> {

  private final Ui ui;
  private final ExpressionEngine expressionEngine;

  @Inject
  EditedCodebaseProcessor(Ui ui, ExpressionEngine expressionEngine) {
    this.ui = ui;
    this.expressionEngine = expressionEngine;
  }

  @Override
  public Codebase createCodebase(EditExpression expression, ProjectContext context)
      throws CodebaseCreationError {
    Codebase codebaseToEdit = expressionEngine.createCodebase(expression.operand(), context);
    String editorName = expression.operation().term().identifier();
    Editor editor = context.editors().get(editorName);
    if (editor == null) {
      throw new CodebaseCreationError("no editor %s", editorName);
    }

    Ui.Task editTask =
        ui.pushTask(
            "edit", "Editing %s with editor %s", codebaseToEdit.path(), editor.getDescription());

    Codebase editedCodebase = editor.edit(codebaseToEdit, expression.operation().term().options());

    ui.popTaskAndPersist(editTask, editedCodebase.path());
    return editedCodebase.copyWithExpression(expression);
  }
}

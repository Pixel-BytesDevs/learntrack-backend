package org.tesis.modulodiagnostico.dtos.extras;

public class QuestionSaveResult {
    private String questionText;
    private boolean success;
    private String message;

    public QuestionSaveResult(String questionText, boolean success, String message) {
        this.questionText = questionText;
        this.success = success;
        this.message = message;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.notes.component;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 *
 * Utility Bean, which is currently used by the NotesApiRestController
 * for formatting error messages with the help of JSON.
 *
 * */

@Component
public final class JSONConstructorImpl implements JSONConstructor {

    @Autowired
    private MessageSource messageSource;

    /**
     *
     * A generic "unexpected" error JSON is returned.
     * This function should not normally be used, unless there are
     * issues with the app front-end, which result in malformed data
     * being sent to the NotesApiRestController.
     *
     * */
    @Override
    public JSONObject constructGenericJsonErrorObjectFromMessage(final String errorMessage) {
        final JSONObject jsonErrorObject = new JSONObject();

        jsonErrorObject.put("error", errorMessage);

        return jsonErrorObject;
    }

    /**
     *
     * Returns an array of Field Errors. These errors are partially expected
     * and will result due to Users attempting to use illegal values for their
     * Note's title and/or content.
     *
     * */

    @Override
    public JSONArray constructJsonFieldErrorsArrayFromBindingResult(BindingResult bindingResult) {
        final JSONArray jsonFieldErrors = new JSONArray();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            final JSONObject jsonFieldError = new JSONObject();

            jsonFieldError.put("field", fieldError.getField());
            jsonFieldError.put("message", messageSource.getMessage(fieldError, null));

            jsonFieldErrors.put(jsonFieldError);
        });

        return jsonFieldErrors;
    }
}

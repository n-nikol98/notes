package com.notes.component;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.validation.BindingResult;

public interface JSONConstructor {

    JSONObject constructGenericJsonErrorObjectFromMessage(String errorMessage);
    JSONArray constructJsonFieldErrorsArrayFromBindingResult(final BindingResult bindingResult);
}

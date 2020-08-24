$(document).ready(function(){
    $('#passwordInputBox').val('')
});

function assurePasswordAndPasswordConfirmationEqualityAndOptionallySubmitRegistrationForm(){
    if ($('#passwordInputBox').val() == $('#passwordConfirmationInputBox').val()) {
        submitForm('registrationForm');
    }
    else {
        $('#passwordConfirmationError').css('display','block');
    }
}
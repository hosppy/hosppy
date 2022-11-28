package club.hosppy.common.validation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PhoneNumberValidator : ConstraintValidator<PhoneNumber?, String?> {
    override fun isValid(phoneNumber: String?, context: ConstraintValidatorContext): Boolean {
        return phoneNumber == null || phoneNumber.matches(Regex("[0-9]{9,13}"))
    }
}
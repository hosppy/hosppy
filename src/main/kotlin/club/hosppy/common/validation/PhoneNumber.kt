package club.hosppy.common.validation

import javax.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [PhoneNumberValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class PhoneNumber(
    val message: String = "Invalid phone number",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)
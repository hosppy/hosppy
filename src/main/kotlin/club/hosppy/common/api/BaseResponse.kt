package club.hosppy.common.api

data class BaseResponse(
    val code: Int = 0,
    val status: String? = null,
    val message: String? = null
)
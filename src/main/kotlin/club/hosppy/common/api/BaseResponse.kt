package club.hosppy.common.api

data class BaseResponse(
    val code: Int = 0,
    val type: String = "",
    val message: String? = ""
)
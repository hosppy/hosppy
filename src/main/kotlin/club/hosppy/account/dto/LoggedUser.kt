package club.hosppy.account.dto

data class LoggedUser(
    var id: Int? = null,
    var email: String? = null,
    var avatarUrl: String? = null
)
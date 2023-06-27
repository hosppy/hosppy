package club.hosppy.account.controller

import club.hosppy.account.annotation.CurrentUser
import club.hosppy.account.dto.LoggedUser
import club.hosppy.account.model.User
import org.modelmapper.ModelMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authentication")
class AuthController(private val modelMapper: ModelMapper) {

    @GetMapping
    fun get(@CurrentUser user: User): LoggedUser {
        return modelMapper.map(user, LoggedUser::class.java)
    }
}
package com.hosppy.common.utils

import com.hosppy.models.AccountMapper
import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration
import org.modelmapper.convention.MatchingStrategies

object Mapper {
    val mapper = ModelMapper()

    init {
        mapper.apply {
            configuration.matchingStrategy = MatchingStrategies.LOOSE
            configuration.fieldAccessLevel = Configuration.AccessLevel.PRIVATE
            configuration.isFieldMatchingEnabled = true
            configuration.isSkipNullEnabled = true
            addMappings(AccountMapper)
        }
    }

    inline fun <S, reified T> convert(source: S): T = mapper.map(source, T::class.java)
}

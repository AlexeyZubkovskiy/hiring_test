package com.hiringtest.matchkeeper.domain.usecase.players

import com.hiringtest.matchkeeper.domain.entities.PlayerTotal
import io.reactivex.Single
import java.util.Optional

interface GetKingOfTheGameUseCase {

	fun getKingOfTheGame(): Single<Optional<PlayerTotal>>

}
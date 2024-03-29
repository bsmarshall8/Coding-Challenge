package com.slack.exercise.dataprovider

import com.slack.exercise.model.UserSearchResult
import io.reactivex.rxjava3.core.Single

/**
 * Provider of [UserSearchResult].
 * This interface abstracts the logic of searching for users through the API or other data sources.
 */
interface UserSearchResultDataProvider {

    /**
     * Returns a [Single] emitting a set of [UserSearchResult].
     */
    fun fetchUsers(searchTerm: String): Single<List<UserSearchResult>>
}
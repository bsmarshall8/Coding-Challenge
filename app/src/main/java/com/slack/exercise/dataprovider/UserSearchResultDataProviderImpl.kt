package com.slack.exercise.dataprovider

import com.slack.exercise.api.SlackApi
import com.slack.exercise.model.UserSearchResult
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Implementation of [UserSearchResultDataProvider].
 */
@Singleton
class UserSearchResultDataProviderImpl @Inject constructor(
    private val slackApi: SlackApi
   // private val userSearchResultDao: UserSearchResultDao
) : UserSearchResultDataProvider {

    /**
     * Returns a [Single] emitting a set of [UserSearchResult].
     */
    override fun fetchUsers(searchTerm: String): Single<List<UserSearchResult>> {
        return slackApi.searchUsers(searchTerm)
            .map { response ->
                val userSearchResults = response.map { user ->
                    UserSearchResult(user.username, user.displayName, user.avatarUrl)
                }
                // Save the results to the local database for offline use
               // saveUsersToLocalDatabase(userSearchResults)
                userSearchResults
            }
    }

//    private fun saveUsersToLocalDatabase(userSearchResults: List<UserSearchResult>) {
//        val dbUserSearchResults = userSearchResults.map { user ->
//            DbUserSearchResult(user.username, user.displayName, user.avatarUrl)
//        }
//        // Save the results to the local database in the background
//        Completable.fromAction {
//            userSearchResultDao.insertUserSearchResults(dbUserSearchResults)
//        }
//            .subscribeOn(Schedulers.io())
//            .subscribe()
//    }
}

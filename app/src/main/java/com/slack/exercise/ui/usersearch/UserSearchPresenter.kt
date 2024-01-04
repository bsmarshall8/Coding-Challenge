package com.slack.exercise.ui.usersearch

import com.slack.exercise.dataprovider.DenylistProvider
import com.slack.exercise.dataprovider.UserSearchResultDataProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

/**
 * Presenter responsible for reacting to user inputs and initiating search queries.
 */
class UserSearchPresenter @Inject constructor(
    private val userNameResultDataProvider: UserSearchResultDataProvider,
    private val denylistProvider: DenylistProvider
) : UserSearchContract.Presenter {

    private var view: UserSearchContract.View? = null
    private val searchQuerySubject = PublishSubject.create<String>()
    private var searchQueryDisposable = Disposable.disposed()
    private var currentSearchTerm = ""
    private val denylist get() = denylistProvider.denylist

    override fun attach(view: UserSearchContract.View) {
        this.view = view
        setUpDisposable()
    }

    private fun setUpDisposable() {
        searchQueryDisposable = searchQuerySubject
            .flatMapSingle { searchTerm ->
                currentSearchTerm = searchTerm
                if (searchTerm.isEmpty() || denylist.contains(searchTerm)) {
                    Timber.i("Search Term $searchTerm was found in denylist")
                    Single.just(emptySet())
                } else {
                    Timber.i("Fetching users from API")
                    userNameResultDataProvider.fetchUsers(searchTerm)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { results ->
                    if (results.isEmpty()) {
                        addSearchTermToDenylist(currentSearchTerm)
                    }
                    this@UserSearchPresenter.view?.onUserSearchResults(results)
                },
                { error ->
                    this@UserSearchPresenter.view?.onUserSearchError(error)
                }
            )
    }

    private fun addSearchTermToDenylist(searchTerm: String?) {
        Timber.i("Search Term - $searchTerm not found. Adding to denylist")
        searchTerm?.let {
            denylist.add(it)
        }
    }

    override fun detach() {
        view = null
        searchQueryDisposable.dispose()
        denylistProvider.persistDenylist()
    }

    override fun onQueryTextChange(searchTerm: String) {
        searchQuerySubject.onNext(searchTerm)
    }
}
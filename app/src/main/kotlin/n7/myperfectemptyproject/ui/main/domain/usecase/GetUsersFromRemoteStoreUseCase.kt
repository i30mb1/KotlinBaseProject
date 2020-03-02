package n7.myperfectemptyproject.ui.main.domain.usecase

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import n7.myperfectemptyproject.data.source.RepositoryImpl
import n7.myperfectemptyproject.data.source.local.db.UsersDao

class GetUsersFromRemoteStoreUseCase @Inject constructor(
    private val repositoryImpl: RepositoryImpl,
    private val userDao: UsersDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun execute(count: Int) = withContext(ioDispatcher) {
        kotlin.runCatching {
            repositoryImpl.getRemoteUsers(count)
        }
    }
}
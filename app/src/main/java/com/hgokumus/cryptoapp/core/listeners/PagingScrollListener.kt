import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PagingScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var isLoading = false
    private var isLastPage = false

    abstract fun loadNextPage()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE
            ) {
                loadNextPage()
            }
        }
    }

    fun setIsLoading(loading: Boolean) {
        isLoading = loading
    }

    fun setIsLastPage(lastPage: Boolean) {
        isLastPage = lastPage
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}

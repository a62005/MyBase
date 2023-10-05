# MyBase

此專案用來記錄自己所寫的架構與學到的新東東

example分支有範例CODE

架構：
1. Kotlin+MVVM
2. BaseFragment、BaseActivity
3. RequestManager：實作BaseRequest，在其中定義param、使用的Service(Retrofit架構)與API，帶入rm唯一與外部接口fun sendReqeust，api完成後會回傳BaseResponse
4. BaseBindingAdapter：仿照QuickAdapter加入ViewBinding，實作provide viewBinding、viewHolder與 convert

技術：
1. Navigation
2. Hilt
3. Room
4. Retrofit
5. Gson
6. Glide
7. EventBus

CustomView：
1. AppItem 用於重複出現的橫向item
2. ActionBar 用於分頁頂部ActionBar

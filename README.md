# MyBase

此專案用來記錄自己所寫的架構與學到的新東東

example分支有範例CODE

環境：2022.2.1 PATCH 2

功能：
1. 寶可夢清單: 依屬性分類，且有我的最愛功能
2. 備忘錄: 與日曆搭配所做的Note List
3. 通知中心: 就只是記錄行為

架構：
1. Kotlin + MVVM + ViewBinding(DataBinding)
2. BaseFragment、BaseActivity、BaseBottomSheetFragment
3. RequestManager：使用Retrofit，request需繼承BaseRequest，並實作定義param、使用的Service與哪個call，並呼叫manager的sendRequest方法帶入request，api完成後會回傳BaseResponse
4. BaseBindingAdapter：仿照BaseQuickAdapter加入ViewBinding

技術：
1. Jetpack：Navigation、Room
2. Coroutine
3. Hilt
4. Retrofit(OkHttp)
5. Gson(Json)
6. Glide
7. EventBus
8. CalendarView

CustomView：
1. AppItem 用於重複出現的橫向item
2. ActionBar 用於分頁頂部ActionBar

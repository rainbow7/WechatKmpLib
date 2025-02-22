package com.youdao.kmp

interface EventHandler {
    fun onReq(req: BaseReq)

    fun onResp(resp: BaseResp)
}
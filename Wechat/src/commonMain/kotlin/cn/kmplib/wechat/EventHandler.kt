package cn.kmplib.wechat

interface EventHandler {
    fun onReq(req: BaseReq)

    fun onResp(resp: BaseResp)
}
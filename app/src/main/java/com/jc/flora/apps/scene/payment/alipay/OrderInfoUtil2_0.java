package com.jc.flora.apps.scene.payment.alipay;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class OrderInfoUtil2_0 {

	/**
	 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
	 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
	 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
	 * orderInfo的获取必须来自服务端；
	 */
	/**
	 * 创建orderInfo
	 * @param amount 金额
	 * @param notifyUrl 异步通知地址
	 * @return
	 */
	public static String createOrderInfo(String body, String subject, String amount, String notifyUrl){
		Map<String, String> params = buildOrderParamMap(Alice.APP_ID, body, subject, amount, notifyUrl);
		String orderStr = buildOrderStr(params, true);
		String sign = buildSign(params, Alice.RSA2_PRIVATE);
		return orderStr + "&" + sign;
	}

	/**
	 * 构造支付订单参数列表
	 * @param appId
	 * @return
	 */
	private static Map<String, String> buildOrderParamMap(String appId, String body, String subject, String amount, String notifyUrl) {
		Map<String, String> keyValues = new HashMap<String, String>();

		// 支付宝分配给开发者的应用ID，如：2013081700024223
		keyValues.put("app_id", appId);

		// 服务接口名称， 固定值
		keyValues.put("method", "alipay.trade.app.pay");

		// 请求使用的编码格式， 固定值
		keyValues.put("charset", "utf-8");

		// 商户生成签名字符串所使用的签名算法类型，推荐使用RSA2
		keyValues.put("sign_type", "RSA2");

		// 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
		keyValues.put("timestamp", getTimestamp());

		// 调用的接口版本，固定为：1.0
		keyValues.put("version", "1.0");

		if(!TextUtils.isEmpty(notifyUrl)){
			// 支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https
			keyValues.put("notify_url", notifyUrl);
		}

		// 业务请求参数的集合，除公共参数外所有请求参数都必须放在这个参数中传递
		keyValues.put("biz_content", buildBizContent(body, subject, amount));

		return keyValues;
	}

	/**
	 * 构造业务参数
	 * @param body
	 * @param subject
	 * @param amount
     * @return 业务请求参数的集合，除公共参数外所有请求参数都必须放在这个参数中传递
     */
	private static String buildBizContent(String body, String subject, String amount){
		StringBuilder sb = new StringBuilder();

		// 开始
		sb.append("{");

		// 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
		appendKeyValue(sb, "body", body).append(",");

		// 商品的标题/交易标题/订单标题/订单关键字等
		appendKeyValue(sb, "subject", subject).append(",");

		// 商户网站唯一订单号
		appendKeyValue(sb, "out_trade_no", getOutTradeNo()).append(",");

		// 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d;若为空，则默认为15d
		appendKeyValue(sb, "timeout_express", "30m").append(",");

		// 订单总金额，单位为元，精确到小数点后两位
		appendKeyValue(sb, "total_amount", amount).append(",");

		// 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
		appendKeyValue(sb, "product_code", "QUICK_MSECURITY_PAY");

		// 结束
		sb.append("}");

		return sb.toString();
	}

	/**
	 * 拼接键值对
	 *
	 * @param sb
	 * @param key
	 * @param value
	 * @return
	 */
	private static StringBuilder appendKeyValue(StringBuilder sb, String key, String value) {
		return sb.append("\"").append(key).append("\"").append(":").append("\"").append(value).append("\"");
	}
	
	/**
	 * 构造支付订单参数信息
	 * 
	 * @param map 支付订单参数
	 * @param isEncode
	 * @return
	 */
	private static String buildOrderStr(Map<String, String> map, boolean isEncode) {
		List<String> keys = new ArrayList<>(map.keySet());
		// key排序
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (int i = 0, size = keys.size(); i < size; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, isEncode));
			if(i != size -1){
				sb.append("&");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	 * 对支付参数信息进行签名
	 * 
	 * @param map 待签名授权信息
	 * @param rsaKey
	 * @return
	 */
	private static String buildSign(Map<String, String> map, String rsaKey) {
		String oriSign = SignUtils.sign(buildOrderStr(map, false), rsaKey, true);
		String encodedSign = "";
		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "sign=" + encodedSign;
	}

	private static String getTimestamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		return format.format(new Date());
	}
	
	/**
	 * 要求外部订单号必须唯一。
	 * @return
	 */
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		String key = format.format(new Date());

		key += new Random().nextInt();
		return key.substring(0, 15);
	}

}
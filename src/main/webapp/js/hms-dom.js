/**
 * write Dom in anguarJs.
 * Not allow to manipulate DOM in anguarJs, but my skill of anguarJs is too low. So, I have to do that.
 */
/**
 * HmsDom
 */
var HmsDom = {
	/**
	 * 初始化bootstrap-select插件
	 * @param selector 选择器
	 * @returns selectpicker
	 */
	initSelectpicker: function(selector) {
		return jQuery((selector || "select")).selectpicker({
			style: "btn-success hms-select select"
		});
	},
	/***
	 * 设置selector val
	 * @param selector 选择器
	 * @param val 值
	 * @returns selectpicker
	 */
	setSelectpickerVal: function(selector, val) {
		return jQuery((selector || "select")).selectpicker("val", val);
	}
};
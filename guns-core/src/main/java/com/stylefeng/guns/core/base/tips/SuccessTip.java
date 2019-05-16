package com.stylefeng.guns.core.base.tips;

/**
 * 返回给前台的成功提示
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:05:22
 */
public class SuccessTip extends Tip {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int id;

	public SuccessTip(){
		super.code = 200;
		super.message = "操作成功";
	}

	public SuccessTip(int id){
		this.id=id;
		super.code = 200;
		super.message = "操作成功";
	}
}

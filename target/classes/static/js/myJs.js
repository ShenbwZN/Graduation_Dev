/**
 * 密码验证：字母，数字，下划线
 */
function isPwd(str) {
    const reg = /^[a-zA-Z][a-zA-Z0-9_]{5,11}$/;
    return reg.test(str);
}

// /**
//  * 是否包含中文
//  */
// function isChinese(str) {
//     const reg = /^[\u4E00-\u9FA5]{2,4}$/;   /*定义验证表达式*/
//     return reg.test(str);     /*进行验证*/
// }

/**
 * 是否包含空格
 */
function isHasSpace(str) {
    return / /.test(str);
}

/*校验是否全由8位数字组成 */
/**
 * 验证码校验，6位数字
 */
function isCode(str) {
    const reg = /^[0-9]{6}$/;   /*定义验证表达式*/
    return reg.test(str);     /*进行验证*/
}

/**
 * 邮箱验证
 */
function isEmail(str) {
    const reg = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
    return reg.test(str);
}

function isUsername(str) {
    const reg = /^[\u4e00-\u9fa5_a-zA-Z0-9]{2,8}$/;
    return reg.test(str) && !isHasSpace(str);
}

/**
 * 邮箱密码登录验证
 */
function verifyPassword() {
    let p_email = document.getElementById("p_email").value;
    let p_pwd = document.getElementById("p_password").value;
    if (isEmail(p_email) && isPwd(p_pwd)) {
        return true;
    } else {
        alert("输入信息有误，请重新输入！+++");
        return false;
    }
}

/**
 * 邮箱验证码登录
 */
function verifyMyCode() {
    let v_email = document.getElementById("v_email").value;
    let v_code = document.getElementById("v_code").value;
    if (isEmail(v_email) && isCode(v_code)) {
        return true;
    } else {
        alert("输入信息有误，请重新输入！");
        return false;
    }
}

/**
 * 注册登录
 */
function verifyRegister() {
    let r_email = document.getElementById("r_email").value;
    let r_username = document.getElementById("r_username").value;
    let r_password = document.getElementById("r_password").value;
    let r_pwd = document.getElementById("r_pwd").value;
    if (isEmail(r_email) && isUsername(r_username) &&
        isPwd(r_pwd) && isPwd(r_password) && r_password === r_pwd) {
        return true;
    } else {
        alert("输入信息有误，请重新输入！");
        return false;
    }
}

/**
 * 重置密码的验证
 */
function verifyModel() {
    let m_email = document.getElementById("m_email").value;
    let m_code = document.getElementById("m_code").value;
    let m_password = document.getElementById("m_password").value;
    if (isEmail(m_email) && isCode(m_code) && isPwd(m_password)) {
        return true;
    } else {
        alert("输入信息有误，请重新输入！");
        return false;
    }
}

/**
 * 导航栏显示时间
 */
function startTime() {
    const today = new Date();
    const weekday = new Array(7);
    weekday[0] = "周日";
    weekday[1] = "周一";
    weekday[2] = "周二";
    weekday[3] = "周三";
    weekday[4] = "周四";
    weekday[5] = "周五";
    weekday[6] = "周六";

    const week = weekday[today.getDay()];
    const h = today.getHours();
    let m = today.getMinutes();
    let s = today.getSeconds(); // 在小于10的数字前加一个‘0’
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('txt_date').innerHTML = week + "，" + h + ":" + m + ":" + s;
    setTimeout(function () {
        startTime()
    }, 500);
}

// 设置格式
function checkTime(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}

/**
 * 倒计时的全局变量
 */
let countdown = 60;

/**
 * 发送邮箱二维码
 */
function sendEmail(obj, email) {
    let s_email = document.getElementById(email).value;
    if (isEmail(s_email)) {
        let flag = false;
        $.get("/send?email=" + s_email, function (data) {
            flag = data;
            if (flag === false) {
                alert("发送失败");
            } else {
                setTime(obj);
            }
        });
    } else {
        alert("邮箱无效，重新输入");
    }
}

/**
 * 按钮设置倒计时
 */
function setTime(obj) {
    if (countdown === 0) {
        obj.removeAttribute("disabled");
        obj.innerHTML = "获取验证码";
        countdown = 60;
        return;
    } else {
        obj.setAttribute("disabled", true);
        obj.innerHTML = "重新发送(" + countdown + ")";
        countdown--;
    }
    setTimeout(function () {
        setTime(obj);
    }, 1000);
}
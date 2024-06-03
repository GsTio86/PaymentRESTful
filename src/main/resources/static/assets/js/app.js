document.addEventListener('DOMContentLoaded', () => {
    const titleElement = document.getElementById('title');
    const subtitleElement = document.getElementById('subtitle');
    const playerImg = document.getElementById('player-image');

    let name = '';
    let rawid = '';
    let token = '';

    window.verify = (recaptchaToken) => {
        token = recaptchaToken;
    };

    window.expired = () => {
        token = '';
    };

    window.error = () => {
        token = '';
    };

    window.submitPayment = () => {
        const username = document.getElementById('username').value.trim();
        const amount = document.getElementById('amount').value.trim();
        const type = document.getElementById("payment").value;

        if (!username) {
            return Swal.fire('請勿留空！', '請輸入Minecraft遊戲ID', 'warning');
        }
        if (!/^[a-zA-Z0-9_]{3,16}$/.test(username)) {
            return Swal.fire('無效輸入！', '遊戲ID格式不正確', 'warning');
        }
        if (!parseFloat(amount) || parseFloat(amount) < 100) {
            return Swal.fire('輸入無效!', '金額必須大於 100 元!', 'warning');
        }
        if (!parseFloat(amount) || parseFloat(amount) > 15000) {
            return Swal.fire('輸入無效!', '金額必須小於 15000 元!', 'warning');
        }
        if (!/^(0|[1-9]\d*)$/.test(amount)) {
            return Swal.fire('輸入無效!', '金額必須為正整數!', 'warning');
        }
        if (type === "NONE") {
            return Swal.fire('付款方式無效!', '請選擇一種付款方式!', 'warning');
        }
        if (!token) {
            return Swal.fire('驗證失敗!', '請完成 Google reCAPTCHA 驗證!', 'warning');
        }

        // 檢查玩家遊戲ID
        checkPlayerID(username).then(playerData => {
            // 如果檢查通過，創建訂單
            createOrder(playerData, amount, type, token);
        }).catch(error => {
            // 處理錯誤
            Swal.fire('錯誤!', error.message, 'error');
        });
    };

    const checkPlayerID = (username) => {
        return new Promise((resolve, reject) => {
            $.ajax({
                // http://127.0.0.1 可以改為你的後端IP或域名 注意 https 和 http
                url: 'http://127.0.0.1/player/check',
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify({ username }),
                success: (json) => {
                    if (json.error) {
                        reject(new Error('無效遊戲ID！這不是正版帳號或從未加入伺服器'));
                    } else {
                        playerImg.src = `https://mc-heads.net/head/${json.uuid}/96`;
                        playerImg.style.display = 'block';
                        titleElement.innerHTML = `你好 ${username}！`;
                        subtitleElement.innerHTML = '請輸入要的金額';
                        rawid = json.rawid;
                        name = json.username;
                        resolve(json);
                    }
                },
                error: (xhr) => {
                    reject(new Error('無法檢查遊戲ID，請稍後再試'));
                }
            });
        });
    };

    const createOrder = (playerData, amount, type, token) => {
        const data = {
            type,
            username: playerData.username,
            rawid: playerData.rawid,
            amount,
            token
        };

        Swal.fire({
            title: '處理中...',
            text: '請稍候',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });

        $.ajax({
            // http://127.0.0.1 可以改為你的後端IP或域名 注意 https 和 http
            url: 'http://127.0.0.1/payment/createOrder',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: (response) => {
                Swal.close();
                document.open();
                document.write(response);
                document.close();
            },
            error: (xhr) => {
                Swal.close();
                Swal.fire('錯誤!', '無法創建訂單，請稍後再試', 'error');
            }
        });
    };

    document.querySelector('.btn-primary.submit-button').addEventListener('click', submitPayment);

    const currentYear = new Date().getFullYear();
    document.getElementById('currentYear').textContent = currentYear;
});

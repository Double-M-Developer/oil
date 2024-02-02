(() => {
    'use strict';

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    const forms = document.querySelectorAll('.needs-validation');

    // Loop over them and prevent submission
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            const newPassword = form.querySelector('#newPassword').value;
            const confirmationPassword = form.querySelector('#confirmationPassword').value;

            // 비밀번호와 비밀번호 확인이 일치하는지 확인
            const passwordsMatch = newPassword === confirmationPassword;

            // 유효성 검사
            if (!form.checkValidity() || !passwordsMatch) {
                event.preventDefault();
                event.stopPropagation();

                // 비밀번호 불일치 메시지 표시
                if (!passwordsMatch) {
                    const confirmationField = form.querySelector('#confirmationPassword');
                    confirmationField.setCustomValidity('비밀번호가 일치하지 않습니다.');
                } else {
                    form.querySelector('#confirmationPassword').setCustomValidity('');
                }
            } else {
                // 모든 조건이 충족되면 CustomValidity를 초기화
                form.querySelector('#confirmationPassword').setCustomValidity('');
            }

            form.classList.add('was-validated');
        }, false);
    });
})();

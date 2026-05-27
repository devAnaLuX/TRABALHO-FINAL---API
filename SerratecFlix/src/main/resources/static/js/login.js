function toast(msg, tipo = 'success') {
    const el = document.getElementById('toast');
    if (!el) return;
    el.textContent = msg;
    el.className = `show ${tipo}`;
    setTimeout(() => el.className = '', 3000);
}

async function realizarLogin(event) {
    if (event) event.preventDefault();

    const email = document.getElementById('username')?.value.trim();
    const senha = document.getElementById('login-senha')?.value.trim();

    if (!email || !senha) { toast('Preencha e-mail e senha.', 'error'); return; }

    try {
        const res = await fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, senha })
        });

        if (res.ok) {
            const data = await res.json();
            localStorage.setItem('token', data.token);
            localStorage.setItem('nomeUsuario', data.nome);
            localStorage.setItem('emailUsuario', data.email);
            toast(`Bem-vindo, ${data.nome}! ✅`);
            setTimeout(() => window.location.href = '../index.html', 1200);
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'E-mail ou senha inválidos.', 'error');
        }
    } catch (e) {
        toast('Falha na conexão com a API.', 'error');
    }
}
const BASE_URL = 'http://localhost:8080/usuarios';

function getToken() { return localStorage.getItem('token'); }
function authHeader() { return { 'Content-Type': 'application/json', 'Authorization': `Bearer ${getToken()}` }; }

function toast(msg, tipo = 'success') {
    const el = document.getElementById('toast');
    if (!el) return;
    el.textContent = msg;
    el.className = `show ${tipo}`;
    setTimeout(() => el.className = '', 3000);
}

function getVal(id) {
    const el = document.getElementById(id);
    return el ? el.value.trim() : '';
}

function showSection(id) {
    document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
    const sec = document.getElementById(id);
    if (sec) sec.classList.add('active');
    const ids = ['listar', 'buscar-id', 'cadastrar', 'atualizar', 'deletar', 'adicionar', 'remover'];
    document.querySelectorAll('.nav-api-btn').forEach((b, i) => {
        b.classList.toggle('active', ids[i] === id);
    });
}

function renderCard(u, container) {
    const card = document.createElement('div');
    card.className = 'card';
    const inicial = (u.nome || '?')[0].toUpperCase();
    const data = u.dataCriacao ? new Date(u.dataCriacao).toLocaleDateString('pt-BR') : '—';
    card.innerHTML = `
        <div class="card-header">${inicial}</div>
        <div class="card-body">
            <div class="title">${u.nome}</div>
            <div class="meta">
                <span class="tag">@${u.username}</span>
                <span class="tag">📅 ${data}</span>
            </div>
            <div class="desc">${u.email}</div>
            <div class="actions">
                <button class="btn btn-secondary btn-sm" onclick='abrirEdicao(${JSON.stringify(u)})'>✏️ Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deletar('${u.id}', this)">🗑 Deletar</button>
            </div>
        </div>`;
    container.appendChild(card);
}

async function carregarUsuarios() {
    const grid = document.getElementById('grid');
    if (!grid) return;
    grid.innerHTML = '<div class="loading">Carregando usuários...</div>';
    try {
        const res = await fetch(BASE_URL, { headers: authHeader() });
        if (!res.ok) throw new Error('Erro ao buscar usuários.');
        const lista = await res.json();
        grid.innerHTML = '';
        if (!lista.length) {
            grid.innerHTML = '<div class="empty"><div class="icon">👤</div><p>Nenhum usuário cadastrado.</p></div>';
        } else {
            lista.forEach(u => renderCard(u, grid));
        }
    } catch (e) {
        grid.innerHTML = `<div class="empty"><div class="icon">⚠️</div><p>${e.message}</p></div>`;
    }
}

async function buscarPorId() {
    const id = getVal('input-id');
    const result = document.getElementById('id-result');
    if (!id) { toast('Cole um UUID válido.', 'error'); return; }
    result.className = 'id-result show';
    result.innerHTML = '<div class="loading">Buscando...</div>';
    try {
        const res = await fetch(`${BASE_URL}/${id}`, { headers: authHeader() });
        if (res.status === 404) throw new Error('Usuário não encontrado.');
        if (!res.ok) throw new Error('Erro ao buscar usuário.');
        const u = await res.json();
        const data = u.dataCriacao ? new Date(u.dataCriacao).toLocaleDateString('pt-BR') : '—';
        result.innerHTML = `
            <h3>${u.nome}</h3>
            <div class="detail-row"><span class="detail-label">ID</span><span class="detail-value">${u.id}</span></div>
            <div class="detail-row"><span class="detail-label">Email</span><span class="detail-value">${u.email}</span></div>
            <div class="detail-row"><span class="detail-label">Username</span><span class="detail-value">@${u.username}</span></div>
            <div class="detail-row"><span class="detail-label">Data Criação</span><span class="detail-value">${data}</span></div>`;
    } catch (e) {
        result.innerHTML = `<div class="empty"><div class="icon">⚠️</div><p>${e.message}</p></div>`;
    }
}

async function cadastrar() {
    const nome     = getVal('c-nome');
    const email    = getVal('c-email');
    const username = getVal('c-username');
    const senha    = getVal('c-senha');

    if (!nome)     { toast('Nome é obrigatório.', 'error'); return; }
    if (!email)    { toast('Email é obrigatório.', 'error'); return; }
    if (!username) { toast('Username é obrigatório.', 'error'); return; }
    if (!senha)    { toast('Senha é obrigatória.', 'error'); return; }
    if (senha.length < 8) { toast('Senha deve ter no mínimo 8 caracteres.', 'error'); return; }

    const body = { nome, email, username, senha };

    try {
        const res = await fetch(BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        if (res.status === 201) {
            toast('Usuário cadastrado! 👤');
            limparForm('c');
            carregarUsuarios();
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao cadastrar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

function abrirEdicao(u) {
    showSection('atualizar');
    document.getElementById('e-id').value       = u.id;
    document.getElementById('e-nome').value     = u.nome     || '';
    document.getElementById('e-email').value    = u.email    || '';
    document.getElementById('e-username').value = u.username || '';
    document.getElementById('e-senha').value    = '';
}

async function atualizar() {
    const id       = getVal('e-id');
    const nome     = getVal('e-nome');
    const email    = getVal('e-email');
    const username = getVal('e-username');
    const senha    = getVal('e-senha');

    if (!id)       { toast('Informe o ID do usuário.', 'error'); return; }
    if (!nome)     { toast('Nome é obrigatório.', 'error'); return; }
    if (!email)    { toast('Email é obrigatório.', 'error'); return; }
    if (!username) { toast('Username é obrigatório.', 'error'); return; }
    if (!senha)    { toast('Senha é obrigatória para atualizar.', 'error'); return; }

    const body = { nome, email, username, senha };

    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'PUT', headers: authHeader(), body: JSON.stringify(body) });
        if (res.ok) {
            toast('Usuário atualizado! ✅');
            showSection('listar');
            carregarUsuarios();
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao atualizar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

async function deletar(id, btn) {
    if (!confirm('Deletar este usuário?')) return;
    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'DELETE', headers: authHeader() });
        if (res.status === 204 || res.ok) {
            toast('Usuário deletado.');
            btn?.closest('.card')?.remove();
        } else { toast('Erro ao deletar.', 'error'); }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

async function deletarPorInput() {
    const id = getVal('d-input-id');
    if (!id) { toast('Informe um UUID.', 'error'); return; }
    await deletar(id, null);
    document.getElementById('d-input-id').value = '';
}

async function atualizarFoto() {
    const id = getVal('foto-id');
    const fileInput = document.getElementById('foto-arquivo');
    if (!id)                        { toast('Informe o ID do usuário.', 'error'); return; }
    if (!fileInput?.files?.length)  { toast('Selecione uma foto.', 'error'); return; }

    const formData = new FormData();
    formData.append('foto', fileInput.files[0]);

    try {
        const res = await fetch(`${BASE_URL}/${id}/foto-perfil`, {
            method: 'POST',
            headers: { 'Authorization': `Bearer ${getToken()}` },
            body: formData
        });
        if (res.ok) {
            toast('Foto atualizada! 📷');
        } else { toast('Erro ao atualizar foto.', 'error'); }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

async function removerFoto() {
    const id = getVal('d-input-id');
    if (!id) { toast('Informe o ID do usuário.', 'error'); return; }
    if (!confirm('Remover foto de perfil?')) return;
    try {
        const res = await fetch(`${BASE_URL}/${id}/foto-perfil`, { method: 'DELETE', headers: authHeader() });
        if (res.status === 204 || res.ok) {
            toast('Foto removida.');
        } else { toast('Erro ao remover foto.', 'error'); }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

function cancelarFoto() { showSection('listar'); }

function limparForm(p) {
    [`${p}-nome`, `${p}-email`, `${p}-username`, `${p}-senha`]
        .forEach(id => { const el = document.getElementById(id); if (el) el.value = ''; });
}

function CancelarForm() { limparForm('e'); showSection('listar'); }

carregarUsuarios();
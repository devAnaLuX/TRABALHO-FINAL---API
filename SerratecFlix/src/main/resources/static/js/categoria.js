const BASE_URL = 'http://localhost:8080/categorias';

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
    const ids = ['listar', 'buscar-id', 'cadastrar', 'atualizar', 'deletar'];
    document.querySelectorAll('.nav-api-btn').forEach((b, i) => {
        b.classList.toggle('active', ids[i] === id);
    });
}

function renderCard(c, container) {
    const card = document.createElement('div');
    card.className = 'card';
    const inicial = (c.nome || '?')[0].toUpperCase();
    card.innerHTML = `
        <div class="card-header">${inicial}</div>
        <div class="card-body">
            <div class="title">${c.nome}</div>
            <div class="desc">${c.descricao || 'Sem descrição.'}</div>
            <div class="actions">
                <button class="btn btn-secondary btn-sm" onclick='abrirEdicao(${JSON.stringify(c)})'>✏️ Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deletar('${c.id}', this)">🗑 Deletar</button>
            </div>
        </div>`;
    container.appendChild(card);
}

async function carregarCategorias() {
    const grid = document.getElementById('grid');
    if (!grid) return;
    grid.innerHTML = '<div class="loading">Carregando categorias...</div>';
    try {
        const res = await fetch(BASE_URL, { headers: authHeader() });
        if (!res.ok) throw new Error('Erro ao buscar categorias.');
        const lista = await res.json();
        grid.innerHTML = '';
        if (!lista.length) {
            grid.innerHTML = '<div class="empty"><div class="icon">🏷️</div><p>Nenhuma categoria cadastrada.</p></div>';
        } else {
            lista.forEach(c => renderCard(c, grid));
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
        if (res.status === 404) throw new Error('Categoria não encontrada.');
        if (!res.ok) throw new Error('Erro ao buscar categoria.');
        const c = await res.json();
        result.innerHTML = `
            <h3>${c.nome}</h3>
            <div class="detail-row"><span class="detail-label">ID</span><span class="detail-value">${c.id}</span></div>
            <div class="detail-row"><span class="detail-label">Descrição</span><span class="detail-value">${c.descricao || '—'}</span></div>`;
    } catch (e) {
        result.innerHTML = `<div class="empty"><div class="icon">⚠️</div><p>${e.message}</p></div>`;
    }
}

async function cadastrar() {
    const nome = getVal('c-nome');
    if (!nome) { toast('Nome é obrigatório.', 'error'); return; }
    const body = { nome, descricao: getVal('c-descricao') || null };
    try {
        const res = await fetch(BASE_URL, { method: 'POST', headers: authHeader(), body: JSON.stringify(body) });
        if (res.status === 201) {
            toast('Categoria cadastrada! 🏷️');
            limparForm('c');
            carregarCategorias();
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao cadastrar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

function abrirEdicao(c) {
    showSection('atualizar');
    document.getElementById('e-id').value       = c.id;
    document.getElementById('e-nome').value     = c.nome      || '';
    document.getElementById('e-descricao').value = c.descricao || '';
}

async function atualizar() {
    const id   = getVal('e-id');
    const nome = getVal('e-nome');
    if (!id)   { toast('Informe o ID da categoria.', 'error'); return; }
    if (!nome) { toast('Nome é obrigatório.', 'error'); return; }
    const body = { nome, descricao: getVal('e-descricao') || null };
    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'PUT', headers: authHeader(), body: JSON.stringify(body) });
        if (res.ok) {
            toast('Categoria atualizada! ✅');
            showSection('listar');
            carregarCategorias();
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao atualizar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

async function deletar(id, btn) {
    if (!confirm('Deletar esta categoria?')) return;
    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'DELETE', headers: authHeader() });
        if (res.status === 204 || res.ok) {
            toast('Categoria deletada.');
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

function limparForm(p) {
    [`${p}-nome`, `${p}-descricao`].forEach(id => { const el = document.getElementById(id); if (el) el.value = ''; });
}

function CancelarForm() { limparForm('e'); showSection('listar'); }

carregarCategorias();
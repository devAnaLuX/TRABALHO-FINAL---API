const BASE_URL = 'http://localhost:8080/listas';

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
    const ids = ['listar', 'buscar-id', 'cadastrar', 'atualizar', 'deletar', 'adicionar'];
    document.querySelectorAll('.nav-api-btn').forEach((b, i) => {
        b.classList.toggle('active', ids[i] === id);
    });
}

function renderCard(l, container) {
    const card = document.createElement('div');
    card.className = 'card';
    const inicial = (l.nomeLista || '?')[0].toUpperCase();
    const data = l.dataCriacao ? new Date(l.dataCriacao).toLocaleDateString('pt-BR') : '—';
    const visib = l.privada ? '🔒 Privada' : '🌐 Pública';
    const filmes = l.filmes?.length ? l.filmes.join(', ') : '—';
    const series = l.series?.length ? l.series.join(', ') : '—';

    card.innerHTML = `
        <div class="card-header">${inicial}</div>
        <div class="card-body">
            <div class="title">${l.nomeLista}</div>
            <div class="meta">
                <span class="tag">${visib}</span>
                <span class="tag">📅 ${data}</span>
            </div>
            <div class="desc">🎬 Filmes: ${filmes}</div>
            <div class="desc">📺 Séries: ${series}</div>
            <div class="actions">
                <button class="btn btn-secondary btn-sm" onclick='abrirEdicao(${JSON.stringify(l)})'>✏️ Editar</button>
                <button class="btn btn-secondary btn-sm" onclick="verRecomendacoes('${l.id}')">🤖 IA</button>
                <button class="btn btn-danger btn-sm" onclick="deletar('${l.id}', this)">🗑 Deletar</button>
            </div>
        </div>`;
    container.appendChild(card);
}

async function carregarListas() {
    const grid = document.getElementById('grid');
    if (!grid) return;
    grid.innerHTML = '<div class="loading">Carregando listas...</div>';
    try {
        const res = await fetch(BASE_URL, { headers: authHeader() });
        if (!res.ok) throw new Error('Erro ao buscar listas.');
        const lista = await res.json();
        grid.innerHTML = '';
        if (!lista.length) {
            grid.innerHTML = '<div class="empty"><div class="icon">⭐</div><p>Nenhuma lista cadastrada.</p></div>';
        } else {
            lista.forEach(l => renderCard(l, grid));
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
        if (res.status === 404) throw new Error('Lista não encontrada.');
        if (!res.ok) throw new Error('Erro ao buscar lista.');
        const l = await res.json();
        const data = l.dataCriacao ? new Date(l.dataCriacao).toLocaleDateString('pt-BR') : '—';
        result.innerHTML = `
            <h3>${l.nomeLista}</h3>
            <div class="detail-row"><span class="detail-label">ID</span><span class="detail-value">${l.id}</span></div>
            <div class="detail-row"><span class="detail-label">Privada</span><span class="detail-value">${l.privada ? 'Sim' : 'Não'}</span></div>
            <div class="detail-row"><span class="detail-label">Data Criação</span><span class="detail-value">${data}</span></div>
            <div class="detail-row"><span class="detail-label">ID Usuário</span><span class="detail-value">${l.usuarioId}</span></div>
            <div class="detail-row"><span class="detail-label">Filmes</span><span class="detail-value">${l.filmes?.join(', ') || '—'}</span></div>
            <div class="detail-row"><span class="detail-label">Séries</span><span class="detail-value">${l.series?.join(', ') || '—'}</span></div>`;
    } catch (e) {
        result.innerHTML = `<div class="empty"><div class="icon">⚠️</div><p>${e.message}</p></div>`;
    }
}

// Converte string separada por vírgulas em array de UUIDs
function parseIds(str) {
    if (!str) return [];
    return str.split(',').map(s => s.trim()).filter(Boolean);
}

async function cadastrar() {
    const nomeLista = getVal('c-nome');
    const usuarioId = getVal('c-username');
    const privadaStr = document.getElementById('c-privada')?.value;

    if (!nomeLista) { toast('Nome da lista é obrigatório.', 'error'); return; }
    if (!usuarioId) { toast('ID do usuário é obrigatório.', 'error'); return; }

    const body = {
        nomeLista,
        privada: privadaStr === 'true',
        usuarioId,
        filmes: parseIds(getVal('c-lista-filmes')),
        series: parseIds(getVal('c-lista-series'))
    };

    try {
        const res = await fetch(BASE_URL, { method: 'POST', headers: authHeader(), body: JSON.stringify(body) });
        if (res.status === 201) {
            toast('Lista criada! ⭐');
            limparForm('c');
            carregarListas();
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao cadastrar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

function abrirEdicao(l) {
    showSection('atualizar');
    document.getElementById('e-id').value           = l.id;
    document.getElementById('e-nome').value         = l.nomeLista  || '';
    document.getElementById('e-username').value     = l.usuarioId  || '';
    document.getElementById('e-privada').value      = l.privada ? 'true' : 'false';
    document.getElementById('e-lista-filmes').value = l.filmes?.join(', ') || '';
    document.getElementById('e-lista-series').value = l.series?.join(', ') || '';
}

async function atualizar() {
    const id        = getVal('e-id');
    const nomeLista = getVal('e-nome');
    const usuarioId = getVal('e-username');
    const privadaStr = document.getElementById('e-privada')?.value;

    if (!id)        { toast('Informe o ID da lista.', 'error'); return; }
    if (!nomeLista) { toast('Nome da lista é obrigatório.', 'error'); return; }
    if (!usuarioId) { toast('ID do usuário é obrigatório.', 'error'); return; }

    const body = {
        nomeLista,
        privada: privadaStr === 'true',
        usuarioId,
        filmes: parseIds(getVal('e-lista-filmes')),
        series: parseIds(getVal('e-lista-series'))
    };

    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'PUT', headers: authHeader(), body: JSON.stringify(body) });
        if (res.ok) {
            toast('Lista atualizada! ✅');
            showSection('listar');
            carregarListas();
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao atualizar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

async function deletar(id, btn) {
    if (!confirm('Deletar esta lista?')) return;
    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'DELETE', headers: authHeader() });
        if (res.status === 204 || res.ok) {
            toast('Lista deletada.');
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

async function verRecomendacoes(id) {
    showSection('adicionar');
    const box = document.getElementById('rec-resultado');
    if (!box) return;
    box.className = 'recomendacao-box show';
    box.textContent = '🤖 Gerando recomendações com IA...';
    try {
        const res = await fetch(`${BASE_URL}/${id}/recomendacoes`, { headers: authHeader() });
        if (!res.ok) throw new Error('Erro ao buscar recomendações.');
        box.textContent = await res.text();
    } catch (e) {
        box.textContent = `Erro: ${e.message}`;
    }
}

async function buscarRecomendacoes() {
    const id = getVal('rec-id');
    if (!id) { toast('Informe o ID da lista.', 'error'); return; }
    await verRecomendacoes(id);
}

function limparForm(p) {
    [`${p}-nome`, `${p}-username`, `${p}-lista-filmes`, `${p}-lista-series`]
        .forEach(id => { const el = document.getElementById(id); if (el) el.value = ''; });
    const priv = document.getElementById(`${p}-privada`);
    if (priv) priv.value = 'false';
}

function CancelarForm() { limparForm('e'); showSection('listar'); }

carregarListas();
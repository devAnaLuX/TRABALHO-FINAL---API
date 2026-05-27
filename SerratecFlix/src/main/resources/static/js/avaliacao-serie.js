const BASE_URL = 'http://localhost:8080/avaliacaoserie';

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

function renderCard(a, container) {
    const card = document.createElement('div');
    card.className = 'card';
    const nota = a.nota != null ? a.nota.toFixed(1) : '—';
    const data = a.dataAvaliacao ? new Date(a.dataAvaliacao).toLocaleDateString('pt-BR') : '—';
    card.innerHTML = `
        <div class="card-header">
            💫
            <div class="rating-badge">${nota}</div>
        </div>
        <div class="card-body">
            <div class="title">${a.tituloSerie || 'Avaliação de Série'}</div>
            <div class="meta">
                <span class="tag">📅 ${data}</span>
                ${a.nomeUsuario ? `<span class="tag">👤 ${a.nomeUsuario}</span>` : ''}
            </div>
            <div class="desc">${a.comentario || 'Sem comentário.'}</div>
            <div class="actions">
                <button class="btn btn-secondary btn-sm" onclick='abrirEdicao(${JSON.stringify(a)})'>✏️ Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deletar('${a.id}', this)">🗑 Deletar</button>
            </div>
        </div>`;
    container.appendChild(card);
}

async function carregarAvaliacoes() {
    const grid = document.getElementById('grid');
    if (!grid) return;
    grid.innerHTML = '<div class="loading">Carregando avaliações...</div>';
    try {
        const res = await fetch(BASE_URL, { headers: authHeader() });
        if (!res.ok) throw new Error('Erro ao buscar avaliações.');
        const lista = await res.json();
        grid.innerHTML = '';
        if (!lista.length) {
            grid.innerHTML = '<div class="empty"><div class="icon">💫</div><p>Nenhuma avaliação cadastrada.</p></div>';
        } else {
            lista.forEach(a => renderCard(a, grid));
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
        if (res.status === 404) throw new Error('Avaliação não encontrada.');
        if (!res.ok) throw new Error('Erro ao buscar avaliação.');
        const a = await res.json();
        const data = a.dataAvaliacao ? new Date(a.dataAvaliacao).toLocaleDateString('pt-BR') : '—';
        result.innerHTML = `
            <h3>${a.tituloSerie || 'Avaliação de Série'}</h3>
            <div class="detail-row"><span class="detail-label">ID</span><span class="detail-value">${a.id}</span></div>
            <div class="detail-row"><span class="detail-label">Nota</span><span class="detail-value">💫 ${a.nota}</span></div>
            <div class="detail-row"><span class="detail-label">Comentário</span><span class="detail-value">${a.comentario || '—'}</span></div>
            <div class="detail-row"><span class="detail-label">Data</span><span class="detail-value">${data}</span></div>
            <div class="detail-row"><span class="detail-label">Usuário</span><span class="detail-value">${a.nomeUsuario || a.usuarioId}</span></div>
            <div class="detail-row"><span class="detail-label">Série</span><span class="detail-value">${a.tituloSerie || a.serieId}</span></div>`;
    } catch (e) {
        result.innerHTML = `<div class="empty"><div class="icon">⚠️</div><p>${e.message}</p></div>`;
    }
}

async function cadastrar() {
    const nota       = getVal('c-nota');
    const comentario = getVal('c-comentario');
    const usuarioId  = getVal('c-usuario');
    const serieId    = getVal('c-serie');

    if (!nota)       { toast('Nota é obrigatória.', 'error'); return; }
    if (!comentario) { toast('Comentário é obrigatório.', 'error'); return; }
    if (!usuarioId)  { toast('ID do usuário é obrigatório.', 'error'); return; }
    if (!serieId)    { toast('ID da série é obrigatório.', 'error'); return; }

    const body = { nota: parseFloat(nota), comentario, usuarioId, serieId };

    try {
        const res = await fetch(BASE_URL, { method: 'POST', headers: authHeader(), body: JSON.stringify(body) });
        if (res.status === 201) {
            toast('Avaliação cadastrada! 💫');
            limparForm('c');
            carregarAvaliacoes();
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao cadastrar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

function abrirEdicao(a) {
    showSection('atualizar');
    document.getElementById('e-id').value         = a.id;
    document.getElementById('e-nota').value       = a.nota       || '';
    document.getElementById('e-comentario').value = a.comentario || '';
    document.getElementById('e-usuario').value    = a.usuarioId  || '';
    document.getElementById('e-serie').value      = a.serieId    || '';
}

async function atualizar() {
    const id         = getVal('e-id');
    const nota       = getVal('e-nota');
    const comentario = getVal('e-comentario');
    const usuarioId  = getVal('e-usuario');
    const serieId    = getVal('e-serie');

    if (!id)         { toast('Informe o ID da avaliação.', 'error'); return; }
    if (!nota)       { toast('Nota é obrigatória.', 'error'); return; }
    if (!comentario) { toast('Comentário é obrigatório.', 'error'); return; }
    if (!usuarioId)  { toast('ID do usuário é obrigatório.', 'error'); return; }
    if (!serieId)    { toast('ID da série é obrigatório.', 'error'); return; }

    const body = { nota: parseFloat(nota), comentario, usuarioId, serieId };

    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'PUT', headers: authHeader(), body: JSON.stringify(body) });
        if (res.ok) {
            toast('Avaliação atualizada! ✅');
            showSection('listar');
            carregarAvaliacoes();
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao atualizar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

async function deletar(id, btn) {
    if (!confirm('Deletar esta avaliação?')) return;
    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'DELETE', headers: authHeader() });
        if (res.status === 204 || res.ok) {
            toast('Avaliação deletada.');
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
    [`${p}-nota`, `${p}-comentario`, `${p}-usuario`, `${p}-serie`]
        .forEach(id => { const el = document.getElementById(id); if (el) el.value = ''; });
}

function CancelarForm() { limparForm('e'); showSection('listar'); }

carregarAvaliacoes();
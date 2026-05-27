const BASE_URL = 'http://localhost:8080/series';

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
    const ids = ['listar', 'buscar-id', 'buscar-titulo', 'cadastrar', 'atualizar', 'deletar'];
    document.querySelectorAll('.nav-api-btn').forEach((b, i) => {
        b.classList.toggle('active', ids[i] === id);
    });
}

let paginaAtual = 0;
let totalPaginas = 1;

async function carregarSeries(pagina = 0) {
    const grid = document.getElementById('grid');
    if (!grid) return;
    grid.innerHTML = '<div class="loading">Carregando séries...</div>';
    try {
        const res = await fetch(`${BASE_URL}?page=${pagina}&size=10&orderBy=titulo`, { headers: authHeader() });
        if (!res.ok) throw new Error('Erro ao buscar séries.');
        const data = await res.json();
        const series = data.content ?? data;
        totalPaginas = data.totalPages ?? 1;
        paginaAtual  = data.number    ?? pagina;

        grid.innerHTML = '';
        if (!series.length) {
            grid.innerHTML = '<div class="empty"><div class="icon">📺</div><p>Nenhuma série cadastrada.</p></div>';
        } else {
            series.forEach(s => renderCard(s, grid));
        }
        const info = document.getElementById('page-info');
        if (info) info.textContent = `Página ${paginaAtual + 1} de ${totalPaginas}`;
        const prev = document.getElementById('btn-prev');
        const next = document.getElementById('btn-next');
        if (prev) prev.disabled = paginaAtual === 0;
        if (next) next.disabled = paginaAtual >= totalPaginas - 1;
    } catch (e) {
        grid.innerHTML = `<div class="empty"><div class="icon">⚠️</div><p>${e.message}</p></div>`;
    }
}

function mudarPagina(delta) {
    const nova = paginaAtual + delta;
    if (nova >= 0 && nova < totalPaginas) carregarSeries(nova);
}

function renderCard(s, container) {
    const card = document.createElement('div');
    card.className = 'card';
    const inicial = (s.titulo || '?')[0].toUpperCase();
    const nota = s.notaMedia != null ? `⭐ ${s.notaMedia.toFixed(1)}` : '';
    const data = s.dataLancamento ? new Date(s.dataLancamento).toLocaleDateString('pt-BR') : '';

    card.innerHTML = `
        <div class="card-header">
            ${inicial}
            ${nota ? `<div class="rating-badge">${nota}</div>` : ''}
        </div>
        <div class="card-body">
            <div class="title" title="${s.titulo}">${s.titulo}</div>
            <div class="meta">
                ${s.temporadas ? `<span class="tag">📅 ${s.temporadas} temp.</span>` : ''}
                ${s.episodios  ? `<span class="tag">🎬 ${s.episodios} ep.</span>` : ''}
                ${data         ? `<span class="tag">📆 ${data}</span>` : ''}
            </div>
            <div class="desc">${s.descricao || 'Sem descrição.'}</div>
            <div class="actions">
                <button class="btn btn-secondary btn-sm" onclick='abrirEdicao(${JSON.stringify(s)})'>✏️ Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deletar('${s.id}', this)">🗑 Deletar</button>
            </div>
        </div>`;
    container.appendChild(card);
}

async function buscarPorId() {
    const id = getVal('input-id');
    const result = document.getElementById('id-result');
    if (!id) { toast('Cole um UUID válido.', 'error'); return; }
    result.className = 'id-result show';
    result.innerHTML = '<div class="loading">Buscando...</div>';
    try {
        const res = await fetch(`${BASE_URL}/${id}`, { headers: authHeader() });
        if (res.status === 404) throw new Error('Série não encontrada.');
        if (!res.ok) throw new Error('Erro ao buscar série.');
        const s = await res.json();
        result.innerHTML = `
            <h3>${s.titulo}</h3>
            <div class="detail-row"><span class="detail-label">ID</span><span class="detail-value">${s.id}</span></div>
            <div class="detail-row"><span class="detail-label">Descrição</span><span class="detail-value">${s.descricao || '—'}</span></div>
            <div class="detail-row"><span class="detail-label">Temporadas</span><span class="detail-value">${s.temporadas ?? '—'}</span></div>
            <div class="detail-row"><span class="detail-label">Episódios</span><span class="detail-value">${s.episodios ?? '—'}</span></div>
            <div class="detail-row"><span class="detail-label">Lançamento</span><span class="detail-value">${s.dataLancamento ? new Date(s.dataLancamento).toLocaleDateString('pt-BR') : '—'}</span></div>
            <div class="detail-row"><span class="detail-label">Nota Média</span><span class="detail-value">${s.notaMedia != null ? '⭐ ' + s.notaMedia.toFixed(1) : '—'}</span></div>`;
    } catch (e) {
        result.innerHTML = `<div class="empty"><div class="icon">⚠️</div><p>${e.message}</p></div>`;
    }
}

async function cadastrar() {
    const titulo     = getVal('c-titulo');
    const descricao  = getVal('c-descricao');
    const temporadas = getVal('c-temporadas');
    const episodios  = getVal('c-episodios');
    if (!titulo || !descricao)        { toast('Título e Descrição são obrigatórios.', 'error'); return; }
    if (!temporadas || !episodios)    { toast('Temporadas e Episódios são obrigatórios.', 'error'); return; }

    const body = { titulo, descricao, temporadas: parseInt(temporadas), episodios: parseInt(episodios) };
    const data = getVal('c-data');
    const nota = getVal('c-nota');
    if (data) body.dataLancamento = data;
    if (nota) body.notaMedia = parseFloat(nota);

    try {
        const res = await fetch(BASE_URL, { method: 'POST', headers: authHeader(), body: JSON.stringify(body) });
        if (res.status === 201) {
            toast('Série cadastrada! 📺');
            limparForm('c');
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao cadastrar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

function abrirEdicao(s) {
    showSection('atualizar');
    document.getElementById('e-id').value         = s.id;
    document.getElementById('e-titulo').value     = s.titulo      || '';
    document.getElementById('e-descricao').value  = s.descricao   || '';
    document.getElementById('e-temporadas').value = s.temporadas  || '';
    document.getElementById('e-episodios').value  = s.episodios   || '';
    document.getElementById('e-data').value       = s.dataLancamento || '';
    document.getElementById('e-nota').value       = s.notaMedia   || '';
}

async function atualizar() {
    const id         = getVal('e-id');
    const titulo     = getVal('e-titulo');
    const descricao  = getVal('e-descricao');
    const temporadas = getVal('e-temporadas');
    const episodios  = getVal('e-episodios');
    if (!id)                          { toast('Informe o ID da série.', 'error'); return; }
    if (!titulo || !descricao)        { toast('Título e Descrição são obrigatórios.', 'error'); return; }
    if (!temporadas || !episodios)    { toast('Temporadas e Episódios são obrigatórios.', 'error'); return; }

    const body = { titulo, descricao, temporadas: parseInt(temporadas), episodios: parseInt(episodios) };
    const data = getVal('e-data');
    const nota = getVal('e-nota');
    if (data) body.dataLancamento = data;
    if (nota) body.notaMedia = parseFloat(nota);

    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'PUT', headers: authHeader(), body: JSON.stringify(body) });
        if (res.ok) {
            toast('Série atualizada! ✅');
            showSection('listar');
            carregarSeries(paginaAtual);
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao atualizar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

async function deletar(id, btn) {
    if (!confirm('Deletar esta série?')) return;
    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'DELETE', headers: authHeader() });
        if (res.status === 204 || res.ok) {
            toast('Série deletada.');
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
    [`${p}-titulo`, `${p}-descricao`, `${p}-temporadas`, `${p}-episodios`, `${p}-data`, `${p}-nota`]
        .forEach(id => { const el = document.getElementById(id); if (el) el.value = ''; });
}

function CancelarForm() { limparForm('e'); showSection('listar'); }

carregarSeries();
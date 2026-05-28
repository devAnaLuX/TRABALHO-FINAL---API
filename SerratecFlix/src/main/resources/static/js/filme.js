const BASE_URL = 'http://localhost:8080/filmes';

function getToken() { return localStorage.getItem('token'); }
function authHeader() { return { 'Content-Type': 'application/json', 'Authorization': `Bearer ${getToken()}` }; }

function toast(msg, tipo = 'success') {
    const el = document.getElementById('toast');
    if (!el) return;
    el.textContent = msg;
    el.className = `show ${tipo}`;
    setTimeout(() => el.className = '', 3000);
}

function classificacaoLabel(c) {
    const map = { LIVRE: 'Livre', DEZ: '10+', DOZE: '12+', QUATORZE: '14+', DEZESSEIS: '16+', DEZOITO: '18+' };
    return map[c] || c || '—';
}

function getVal(id) {
    const el = document.getElementById(id);
    return el ? el.value.trim() : '';
}

function showSection(id) {
    document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
    const sec = document.getElementById(id);
    if (sec) sec.classList.add('active');
    const ids = ['listar', 'buscar-id', 'buscar-titulo', 'buscar-categoria', 'cadastrar', 'atualizar', 'deletar'];
    document.querySelectorAll('.nav-api-btn').forEach((b, i) => {
        b.classList.toggle('active', ids[i] === id);
    });
}

let paginaAtual = 0;
let totalPaginas = 1;

async function carregarFilmes(pagina = 0) {
    const grid = document.getElementById('grid');
    if (!grid) return;
    grid.innerHTML = '<div class="loading">Carregando filmes...</div>';
    try {
        const res = await fetch(`${BASE_URL}?page=${pagina}&size=10&orderBy=titulo`, { headers: authHeader() });
        if (!res.ok) throw new Error('Erro ao buscar filmes.');
        const data = await res.json();
        const filmes = data.content ?? data;
        totalPaginas = data.totalPages ?? 1;
        paginaAtual  = data.number    ?? pagina;
        grid.innerHTML = '';
        if (!filmes.length) {
            grid.innerHTML = '<div class="empty"><div class="icon">🎬</div><p>Nenhum filme cadastrado ainda.</p></div>';
        } else {
            filmes.forEach(f => renderCard(f, grid));
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
    if (nova >= 0 && nova < totalPaginas) carregarFilmes(nova);
}

function renderCard(f, container) {
    const card = document.createElement('div');
    card.className = 'card';
    const inicial = (f.titulo || '?')[0].toUpperCase();
    const nota    = f.notaMedia != null ? `⭐ ${f.notaMedia.toFixed(1)}` : '';
    const duracao = f.duracao ? `${f.duracao} min` : '';
    const data    = f.dataLancamento ? new Date(f.dataLancamento).toLocaleDateString('pt-BR') : '';
    const classif = classificacaoLabel(f.classificacaoIndicativa);
    card.innerHTML = `
        <div class="card-header">
            ${inicial}
            ${nota ? `<div class="rating-badge">${nota}</div>` : ''}
        </div>
        <div class="card-body">
            <div class="title" title="${f.titulo}">${f.titulo}</div>
            <div class="meta">
                ${duracao ? `<span class="tag">⏱ ${duracao}</span>` : ''}
                ${data    ? `<span class="tag">📅 ${data}</span>`   : ''}
                ${f.classificacaoIndicativa ? `<span class="tag classificacao">${classif}</span>` : ''}
            </div>
            <div class="desc">${f.descricao || 'Sem descrição.'}</div>
            <div class="actions">
                <button class="btn btn-secondary btn-sm" onclick='abrirEdicao(${JSON.stringify(f)})'>✏️ Editar</button>
                <button class="btn btn-danger btn-sm"    onclick="deletar('${f.id}', this)">🗑 Deletar</button>
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
        if (res.status === 404) throw new Error('Filme não encontrado.');
        if (!res.ok) throw new Error('Erro ao buscar filme.');
        const f = await res.json();
        result.innerHTML = `
            <h3>${f.titulo}</h3>
            <div class="detail-row"><span class="detail-label">ID</span><span class="detail-value">${f.id}</span></div>
            <div class="detail-row"><span class="detail-label">Descrição</span><span class="detail-value">${f.descricao || '—'}</span></div>
            <div class="detail-row"><span class="detail-label">Duração</span><span class="detail-value">${f.duracao ? f.duracao + ' min' : '—'}</span></div>
            <div class="detail-row"><span class="detail-label">Lançamento</span><span class="detail-value">${f.dataLancamento ? new Date(f.dataLancamento).toLocaleDateString('pt-BR') : '—'}</span></div>
            <div class="detail-row"><span class="detail-label">Classificação</span><span class="detail-value">${classificacaoLabel(f.classificacaoIndicativa)}</span></div>
            <div class="detail-row"><span class="detail-label">Nota Média</span><span class="detail-value">${f.notaMedia != null ? '⭐ ' + f.notaMedia.toFixed(1) : '—'}</span></div>`;
    } catch (e) {
        result.innerHTML = `<div class="empty"><div class="icon">⚠️</div><p>${e.message}</p></div>`;
    }
}

async function buscarPorTitulo() {
    const titulo = getVal('input-titulo');
    if (!titulo) { toast('Digite um título.', 'error'); return; }
    const grid = document.getElementById('busca-grid');
    grid.innerHTML = '<div class="loading">Buscando...</div>';
    try {
        const res = await fetch(`${BASE_URL}/buscar?titulo=${encodeURIComponent(titulo)}`, { headers: authHeader() });
        if (!res.ok) throw new Error('Erro na busca.');
        const filmes = await res.json();
        grid.innerHTML = '';
        if (!filmes.length) {
            grid.innerHTML = '<div class="empty"><div class="icon">🔍</div><p>Nenhum resultado.</p></div>';
            return;
        }
        filmes.forEach(f => renderCard(f, grid));
    } catch (e) {
        grid.innerHTML = `<div class="empty"><div class="icon">⚠️</div><p>${e.message}</p></div>`;
    }
}

async function buscarPorCategoria() {
    const categoriaId = getVal('input-categoria');
    if (!categoriaId) { toast('Cole o UUID da categoria.', 'error'); return; }
    const grid = document.getElementById('categoria-grid');
    grid.innerHTML = '<div class="loading">Buscando...</div>';
    try {
        const res = await fetch(`${BASE_URL}/categoria/${encodeURIComponent(categoriaId)}`, { headers: authHeader() });
        if (res.status === 404) throw new Error('Categoria não encontrada.');
        if (!res.ok) throw new Error('Erro na busca por categoria.');
        const filmes = await res.json();
        grid.innerHTML = '';
        if (!filmes.length) {
            grid.innerHTML = '<div class="empty"><div class="icon">🏷️</div><p>Nenhum filme nesta categoria.</p></div>';
            return;
        }
        filmes.forEach(f => renderCard(f, grid));
    } catch (e) {
        grid.innerHTML = `<div class="empty"><div class="icon">⚠️</div><p>${e.message}</p></div>`;
    }
}

async function cadastrar() {
    const titulo    = getVal('c-titulo');
    const descricao = getVal('c-descricao');
    if (!titulo || !descricao) { toast('Título e Descrição são obrigatórios.', 'error'); return; }
    const body    = { titulo, descricao };
    const duracao = getVal('c-duracao');
    const data    = getVal('c-data');
    const classif = getVal('c-classificacao');
    const nota    = getVal('c-nota');
    if (duracao) body.duracao = parseInt(duracao);
    if (data)    body.dataLancamento = data;
    if (classif) body.classificacaoIndicativa = classif;
    if (nota)    body.notaMedia = parseFloat(nota);
    try {
        const res = await fetch(BASE_URL, { method: 'POST', headers: authHeader(), body: JSON.stringify(body) });
        if (res.status === 201) {
            toast('Filme cadastrado! 🎬');
            limparForm('c');
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao cadastrar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

function abrirEdicao(f) {
    showSection('atualizar');
    document.getElementById('e-id').value            = f.id;
    document.getElementById('e-titulo').value        = f.titulo    || '';
    document.getElementById('e-descricao').value     = f.descricao || '';
    document.getElementById('e-duracao').value       = f.duracao   || '';
    document.getElementById('e-data').value          = f.dataLancamento || '';
    document.getElementById('e-classificacao').value = f.classificacaoIndicativa || '';
    document.getElementById('e-nota').value          = f.notaMedia || '';
}

async function atualizar() {
    const id        = getVal('e-id');
    const titulo    = getVal('e-titulo');
    const descricao = getVal('e-descricao');
    if (!id)                   { toast('Informe o ID do filme.', 'error'); return; }
    if (!titulo || !descricao) { toast('Título e Descrição são obrigatórios.', 'error'); return; }
    const body    = { titulo, descricao };
    const duracao = getVal('e-duracao');
    const data    = getVal('e-data');
    const classif = getVal('e-classificacao');
    const nota    = getVal('e-nota');
    if (duracao) body.duracao = parseInt(duracao);
    if (data)    body.dataLancamento = data;
    if (classif) body.classificacaoIndicativa = classif;
    if (nota)    body.notaMedia = parseFloat(nota);
    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'PUT', headers: authHeader(), body: JSON.stringify(body) });
        if (res.ok) {
            toast('Filme atualizado! ✅');
            showSection('listar');
            carregarFilmes(paginaAtual);
        } else {
            const err = await res.json();
            toast(err.erros?.[0] || 'Erro ao atualizar.', 'error');
        }
    } catch (e) { toast('Falha na conexão.', 'error'); }
}

async function deletar(id, btn) {
    if (!confirm('Deletar este filme?')) return;
    try {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'DELETE', headers: authHeader() });
        if (res.status === 204 || res.ok) {
            toast('Filme deletado.');
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
    [`${p}-titulo`, `${p}-descricao`, `${p}-duracao`, `${p}-data`, `${p}-nota`]
        .forEach(id => { const el = document.getElementById(id); if (el) el.value = ''; });
    const cl = document.getElementById(`${p}-classificacao`);
    if (cl) cl.value = '';
}

function CancelarForm() { limparForm('e'); showSection('listar'); }

carregarFilmes();
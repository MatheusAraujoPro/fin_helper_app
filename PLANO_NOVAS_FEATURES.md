# Sugestões de Features — Fin Helper

## O que já existe
- CRUD completo de transações (Nubank, Genial, Saldo)
- Resumo por tipo (header cards)
- Busca por nome
- Swipe para deletar

## Features sugeridas (por prioridade)

### Alta prioridade (mais impacto no uso diário)

1. **Filtro por período/mês**
   - Seletor de mês/ano no topo da tela
   - Totais e lista filtrados pelo período selecionado
   - Simples de implementar: filtro local na ViewModel + DatePicker

2. **Agrupamento por data**
   - Agrupar transações por dia/semana na lista
   - Cabeçalhos de seção com data e subtotal do dia
   - Melhora muito a legibilidade

3. **Categorias de transação**
   - Tags como: Alimentação, Transporte, Lazer, Saúde, etc.
   - Adiciona coluna `category` na tabela de transações
   - Permite filtrar/agrupar por categoria

4. **Gráficos / Dashboard**
   - Pizza de gastos por categoria
   - Barras de receita vs despesa por mês
   - Biblioteca: Vico ou MPAndroidChart (Compose-friendly)

### Média prioridade

5. **Transações recorrentes**
   - Marcar uma transação como "repetir mensalmente"
   - WorkManager para gerar automaticamente a cada ciclo
   - Ex: salário, aluguel, assinaturas

6. **Metas / Orçamento por categoria**
   - Definir limite de gasto mensal por categoria
   - Indicador visual de quanto foi consumido do orçamento

7. **Tela de detalhes da transação**
   - Campo de notas/observação livre
   - Histórico de edições
   - Nova rota na navegação: `transaction_detail_screen/{id}`

8. **Tela de configurações**
   - Idioma (já existe o enum `Language`)
   - Moeda padrão
   - Tema claro/escuro

### Baixa prioridade (nice to have)

9. **Exportar para CSV/PDF**
   - Botão de exportação no menu
   - Compartilhar via ShareSheet do Android

10. **Busca avançada com filtros**
    - Além do nome: filtrar por tipo (receita/despesa), intervalo de valor, período
    - Chips de filtros ativos sobre a lista

11. **Backup / Sync em nuvem**
    - Firebase Firestore ou Google Drive
    - Requer autenticação de usuário

## Recomendação de ordem de implementação
1. Filtro por mês → impacto imediato
2. Agrupamento por data → melhora UX sem mudar modelos
3. Categorias → abre espaço para gráficos
4. Gráficos → fecha o ciclo de analytics básico

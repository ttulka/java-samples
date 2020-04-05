package com.ttulka.ecommerce.common.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Sortable and limitable JDBC entries.
 */
@Builder(access = AccessLevel.PRIVATE, toBuilder = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JdbcEntries {

    final @NonNull String query;
    final @NonNull List<Object> queryParams;
    private final List<String> sortedBy;

    private final int start;
    private final int limit;

    private final @NonNull JdbcTemplate jdbcTemplate;

    /**
     * @param query        the SQL query string with <code>%s</code> place holders for sorted by and <code>?</code> for value placeholders.
     * @param queryParams  the value placeholders values
     * @param jdbcTemplate the JdbcTemplate
     */
    public JdbcEntries(@NonNull String query, @NonNull List<Object> queryParams, @NonNull JdbcTemplate jdbcTemplate) {
        this.query = query;
        this.queryParams = queryParams;
        this.jdbcTemplate = jdbcTemplate;
        this.start = 0;
        this.limit = Integer.MAX_VALUE;
        this.sortedBy = List.of("id");
    }

    /**
     * Returns entries as a list.
     *
     * @return the list of entries
     */
    public List<Map<String, Object>> list() {
        return jdbcTemplate.queryForList(
                String.format(query.concat(" ORDER BY %s LIMIT ?,?"), sortedBy.toArray()),
                merged(queryParams, start, limit));
    }

    /**
     * Returns sorted entries.
     *
     * @param by the list of sorted by
     * @return sorted entries
     */
    public JdbcEntries sorted(List<String> by) {
        return toBuilder().sortedBy(by).build();
    }

    /**
     * Returns sorted entries.
     *
     * @param by the sorted by
     * @return sorted entries
     */
    public JdbcEntries sorted(String by) {
        return toBuilder().sortedBy(List.of(by)).build();
    }

    /**
     * Returns limited entries.
     *
     * @param start the start of the limit
     * @param limit the items count of the limit
     * @return limited entries.
     */
    public JdbcEntries limited(int start, int limit) {
        return toBuilder().start(start).limit(limit).build();
    }

    private Object[] merged(List<Object> args1, Object... args2) {
        Object[] array = new Object[args1.size() + args2.length];
        int i = 0;
        for (Object o : args1) {
            array[i++] = o;
        }
        for (Object o : args2) {
            array[i++] = o;
        }
        return array;
    }
}
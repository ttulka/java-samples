package com.ttulka.junit5;

import java.util.Arrays;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

class DisabledOnTagExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        if (context.getRequiredTestMethod().isAnnotationPresent(DisabledOnTag.class)) {
            if (Arrays.stream(context.getRequiredTestMethod().getAnnotationsByType(DisabledOnTag.class))
                    .map(DisabledOnTag::value)
                    .anyMatch(context.getTags()::contains)) {
                return ConditionEvaluationResult.disabled("DisabledOnTag");
            }
        }
        return ConditionEvaluationResult.enabled("Enabled");
    }
}

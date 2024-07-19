package com.maizi.author;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AllMatchExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // 检查列表中所有元素是否都不为空
        boolean allNonNull = numbers.stream().allMatch(Objects::nonNull);

        System.out.println("所有元素都不为空: " + allNonNull); // 输出: 所有元素都不为空: true

        // 包含一个空元素的列表
        List<Integer> numbersWithNull = Arrays.asList(1, 2, null, 4, 5);

        // 检查列表中所有元素是否都不为空
        boolean allNonNullWithNull = numbersWithNull.stream().allMatch(Objects::nonNull);

        System.out.println("所有元素都不为空: " + allNonNullWithNull); // 输出: 所有元素都不为空: false
    }
}

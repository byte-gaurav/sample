package org.sample.dsa;

import java.util.Arrays;

public class CoinChange {

    public static void main(String[] args) {
        CoinChange coinChange = new CoinChange();
        int[] coins = {1};
        int amount = 3;
        CountChange countChange = coinChange.new CoinChangeMemoisation();
        // CountChange countChange = coinChange.new CoinChangeRecursion();
        System.out.println("Result : " + countChange.coinChange(coins, amount));
    }


    public interface CountChange{
        int coinChange(int[] coins, int amount);
    }

    class CoinChangeMemoisation implements CountChange {
        @Override
        public int coinChange(int[] coins, int amount) {
            int[] memo = new int[amount + 1];
            Arrays.fill(memo, -1);
            int minCoins = countCoinChangeMemoisation(coins, amount, memo);
            return minCoins == Integer.MAX_VALUE ? -1 : minCoins;
        }

        private int countCoinChangeMemoisation(int[] coins, int amount, int[] memo) {
            if (amount == 0) {
                return 0;
            }
            if (amount < 0) {
                return Integer.MAX_VALUE;
            }
            if (memo[amount] != -1) {
                return memo[amount];
            }
            int minCoins = Integer.MAX_VALUE;
            for (int coin : coins) {
                int res = countCoinChangeMemoisation(coins, amount - coin, memo);
                if (res != Integer.MAX_VALUE) {
                    minCoins = Math.min(minCoins, res + 1);
                }
            }
            memo[amount] = minCoins;
            return minCoins;
        }
    }

    class CoinChangeRecursion implements CountChange {
        @Override
        public int coinChange(int[] coins, int amount) {
            int count = countCoinChange(coins, amount, 0, 0);
            return count == Integer.MAX_VALUE ? -1 : count;
        }

        private int countCoinChange(int[] coins, int amount, int currentSum, int count) {
            if (currentSum == amount) {
                return count;
            }
            if (currentSum > amount) {
                return 0;
            }
            int minCount = Integer.MAX_VALUE;
            for (int coin : coins) {
                int currentCount = countCoinChange(coins, amount, currentSum + coin, count+1);
                if (currentCount == 0) {
                    continue;
                }
                minCount = Math.min(minCount, currentCount);

            }
            return minCount;
        }
    }
}

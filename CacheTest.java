import java.util.HashMap;
import java.util.TreeMap;

public class CacheTest {
    
    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Usage: java CacheTest <cache-size> <number-of-Webpages> <standard-deviation> <debug-level=0-3> [<seed>]");
            return;
        }

        int cacheSize = Integer.parseInt(args[0]);
        int numberOfWebpages = Integer.parseInt(args[1]);
        int debugLevel = Integer.parseInt(args[3]);
        long seed = args.length == 5 ? Long.parseLong(args[4]) : System.currentTimeMillis();
        Cache<String, Webpage> myCache = new Cache<>(cacheSize);
        LoremIpsumGenerator generator = new LoremIpsumGenerator();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfWebpages; i++) {
            int randomPage = (int)(Math.random() * (i + 1)); 
            String url = "www.webpage" + randomPage + ".com";

            Webpage page = myCache.get(url);
            if (page == null) {
                String content = generator.getLoremIpsum(seed);
                page = new Webpage(url, content);
                myCache.add(page);
            }
            if (debugLevel ==2) {
                System.out.println(page.getWebpageURL() + ": " + page.getSummarizedWebpageContent());
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        printCacheStats(myCache, numberOfWebpages, elapsedTime);
        if (debugLevel == 1) {
            printDistribution(myCache);
        }
    }
    // Needed a lot of help on this part. Not sure if I got this right. My outputs aren't lining up with
    // the sample outputs. However, I will take a deeper dive into this for the next due date!

    private static void printCacheStats(Cache<String, Webpage> cache, int totalRefs, long elapsedTime) {
        System.out.println("Total number of references:        " + totalRefs);
        System.out.println("Total number of cache hits:        " + cache.getHits());
        double hitPercentage = ((double) cache.getHits() / totalRefs) * 100;
        System.out.printf("Cache hit percent:                 %.2f%%\n", hitPercentage);
        System.out.println("----------------------------------------------------------------");
        System.out.println("Time elapsed: " + elapsedTime / 1000.0 + " milliseconds");
        System.out.println("----------------------------------------------------------------");
    }

    private static void printDistribution(Cache<String, Webpage> cache) {
        System.out.println("--------------------------------------");
        System.out.println("Printing the Webpage Distribution:");
        System.out.println("--------------------------------------");
        HashMap<String, Integer> accessFrequency = cache.getAccessFrequency();
        TreeMap<String, Integer> sortedMap = new TreeMap<>(accessFrequency);
        sortedMap.forEach((k, v) -> {
            System.out.println("[" + k + "]: " + v);
        });
    }
}

package com.example.zephyrus;

public class TarotCardReaderConfig {
    public enum TarotCardTypes {ALL, NORMAL, REVERSED, MAJOR_ARCANA, SWORDS, WANDS, CUPS, PENTACLES};

    private static final int allCardIdStart = 0;
    private static final int allCardIdEnd = 155;
    private static final int allCardIdStride = 1;

    private static final int normalCardIdStart = 0;
    private static final int normalCardIdEnd = 154;
    private static final int normalCardIdStride = 2;

    private static final int reversedCardIdStart = 1;
    private static final int reversedCardIdEnd = 155;
    private static final int reversedCardIdStride = 2;

    private static final int majorArcanaCardIdStart = 0;
    private static final int majorArcanaCardIdEnd = 43;
    private static final int majorArcanaCardIdStride = 1;

    private static final int wandsCardIdStart = 44;
    private static final int wandsCardIdEnd = 71;
    private static final int wandsCardIdStride = 1;

    private static final int cupsCardIdStart = 72;
    private static final int cupsCardIdEnd = 99;
    private static final int cupsCardIdStride = 1;

    private static final int swordsCardIdStart = 100;
    private static final int swordsCardIdEnd = 127;
    private static final int swordsCardIdStride = 1;

    private static final int pentaclesCardIdStart = 128;
    private static final int pentaclesCardIdEnd = 155;
    private static final int pentaclesCardIdStride = 1;



    private final int startID, endID, stride;
    private TarotCardReaderConfig(int startID, int endID, int stride)
    {
        this.startID = startID;
        this.endID = endID;
        this.stride = stride;
    }
    public int getStartID() {
        return startID;
    }

    public int getEndID() {
        return endID;
    }

    public int getStride() {
        return stride;
    }


    public static TarotCardReaderConfig getConfigForType(TarotCardTypes cardTypes)
    {
        switch (cardTypes)
        {
            case ALL:
                return new TarotCardReaderConfig(allCardIdStart, allCardIdEnd, allCardIdStride);
            case NORMAL:
                return new TarotCardReaderConfig(normalCardIdStart, normalCardIdEnd, normalCardIdStride);
            case REVERSED:
                return new TarotCardReaderConfig(reversedCardIdStart, reversedCardIdEnd, reversedCardIdStride);
            case MAJOR_ARCANA:
                return new TarotCardReaderConfig(majorArcanaCardIdStart, majorArcanaCardIdEnd, majorArcanaCardIdStride);
            case SWORDS:
                return new TarotCardReaderConfig(swordsCardIdStart, swordsCardIdEnd, swordsCardIdStride);
            case WANDS:
                return new TarotCardReaderConfig(wandsCardIdStart, wandsCardIdEnd, wandsCardIdStride);
            case CUPS:
                return new TarotCardReaderConfig(cupsCardIdStart, cupsCardIdEnd, cupsCardIdStride);
            case PENTACLES:
                return new TarotCardReaderConfig(pentaclesCardIdStart, pentaclesCardIdEnd, pentaclesCardIdStride);
        }
        throw new IllegalArgumentException("Must pass a valid TarotCardTypes.");
    }

    public int getNumberOfCards()
    {
        double width = endID - startID + 1;
        return (int) Math.ceil(width / stride);
    }

}

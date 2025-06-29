package com.kevinqiu.langchain4jdemo;

import dev.langchain4j.community.model.dashscope.QwenTokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.*;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.apache.poi.hpsf.Array;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static dev.langchain4j.internal.Utils.randomUUID;

@SpringBootTest
public class RAGTest {

    private final String basePath = "/Users/kevinqiu/Desktop/JavaToGithub/Java_Projects/langchain4j/langchain4j-demo/src/main/resources/";

    @Autowired
    private EmbeddingModel embeddingModel;

    @Test
    public void testReadDocument() {
        Document document = FileSystemDocumentLoader.loadDocument(basePath + "knowledge/神经内科.md");
        System.out.println(document.text());
    }

    @Test
    public void testParsePDF() {
        Document document = FileSystemDocumentLoader.loadDocument(
                basePath + "knowledge/医院信息.pdf",
                new ApachePdfBoxDocumentParser()
        );
        System.out.println(document.text());
    }

    @Test
    public void testDocumentSplitter() {

        Document document = FileSystemDocumentLoader.loadDocument(basePath + "knowledge/人工智能.md");

        // 暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // 按段落分割文档：每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
        // 注意：当段落长度总和小于设定的最大长度时，就不会有重叠的必要。
        DocumentByParagraphSplitter documentByParagraphSplitter = new DocumentByParagraphSplitter(
                300, 30, new HuggingFaceTokenizer()
        );

        EmbeddingStoreIngestor
                .builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(documentByParagraphSplitter)
                .build()
                .ingest(document);


        System.out.println(embeddingStore);
    }

    @Test
    public void testTokenCount() {
        String text = "这是一个实例文本，用于测试 token 的长度";
        UserMessage userMessage = UserMessage.userMessage(text);

        // 计算 token 的长度
        HuggingFaceTokenizer huggingFaceTokenizer = new HuggingFaceTokenizer();
        int countInMessage = huggingFaceTokenizer.estimateTokenCountInMessage(userMessage);
        System.out.println("token 长度：" + countInMessage);

    }

    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;

    @Test
    public void testPineconeEmbed() throws InterruptedException {
        TextSegment segment1 = TextSegment.from("我喜欢羽毛球");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        // 存入向量数据库
        embeddingStore.add(embedding1, segment1);

        TextSegment segment2 = TextSegment.from("今天天气真不错");
        Embedding embedding2 = embeddingModel.embed(segment2).content();
        // 存入向量数据库
        embeddingStore.add(embedding2, segment2);
    }

    @Test
    public void testPineconeSearch() {

        // 将语句转成向量数据
        Embedding queryEmbedding = embeddingModel.embed("我喜欢运动").content();
//        System.out.println(queryEmbedding.vector());
        // 创建搜索请求对象
        EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1)
                .build();

        // 根据搜索请求在向量存储中进行相似度搜索
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(searchRequest);

        //searchResult.matches()：获取搜索结果中的匹配项列表。
        //.get(0)：从匹配项列表中获取第一个匹配项
        EmbeddingMatch<TextSegment> embeddingMatch = searchResult.matches().get(0);

        //获取匹配项的相似度得分
        System.out.println(embeddingMatch.score()); // 0.8144288515898701

        //返回文本结果
        System.out.println(embeddingMatch.embedded().text());
    }

    @Test
    public void testUploadKnowledgeLib() {
        Document document1 = FileSystemDocumentLoader.loadDocument(basePath + "knowledge/医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument(basePath + "knowledge/科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument(basePath + "knowledge/神经内科.md");
        List<Document> documents = Arrays.asList(document1, document2, document3);


        EmbeddingStoreIngestor
                .builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build()
                .ingest(documents);


    }

}

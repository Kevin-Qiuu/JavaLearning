package com.kevinqiu.langchain4jdemo.config;

import com.kevinqiu.langchain4jdemo.store.MongoChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
public class XiaozhiAgentConfig {

    private final String basePath = "/Users/kevinqiu/Desktop/JavaToGithub/Java_Projects/langchain4j/langchain4j-demo/src/main/resources/";

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore embeddingStore;

    @Bean
    public ChatMemoryProvider xiaozhiChatMemoryProvider() {
//        return new ChatMemoryProvider() {
//            @Override
//            public ChatMemory get(Object memoryId) {
//                return MessageWindowChatMemory
//                        .builder()
//                        .id(memoryId)
//                        .chatMemoryStore(mongoChatMemoryStore)
//                        .build();
//            }
//        }
        return memoryId -> MessageWindowChatMemory.builder().id(memoryId).maxMessages(20).chatMemoryStore(mongoChatMemoryStore).build();
    }

//    @Bean
//    ContentRetriever xiaozhiContentRetriever() {
//        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
//        // 并使用默认的文档解析器对文档进行解析
//        Document document1 = FileSystemDocumentLoader.loadDocument(basePath + "knowledge/医院信息.md");
//        Document document2 = FileSystemDocumentLoader.loadDocument(basePath + "knowledge/科室信息.md");
//        Document document3 = FileSystemDocumentLoader.loadDocument(basePath + "knowledge/神经内科.md");
//        List<Document> documents = Arrays.asList(document1, document2, document3);
//
//        //使用内存向量存储
//        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
//
//        //使用默认的文档分割器
//        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
//
//        //从嵌入存储（EmbeddingStore）里检索和查询内容相关的信息
//        return EmbeddingStoreContentRetriever.from(embeddingStore);
//    }

    @Bean
    ContentRetriever xiaozhiPinconeContentRetriever() {

//        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        return EmbeddingStoreContentRetriever
                .builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .maxResults(1)
                .minScore(0.75)
                .build();
    }

}

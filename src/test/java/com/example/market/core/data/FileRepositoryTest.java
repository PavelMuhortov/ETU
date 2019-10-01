package com.example.market.core.data;

import com.example.market.core.model.BaseModel;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Objects;

public class FileRepositoryTest {

    private static int counter = 0;

    private Repository<TestModel> repository;

    @Before
    public void setup() throws IOException {
        repository = new FileRepository<>(Files.createTempFile("oop_lab_", "_" + counter++).toFile(), TestModel::new);
//        repository = new XmlRepository<>(Files.createTempFile("oop_lab_", "_" + counter++ + ".xml").toFile(), TestModel::new);
//        repository = new InMemoryStorage<>();
    }

    @Test
    public void saveAndDelete() {
        TestModel data1 = new TestModel("data1", 41);
        TestModel data2 = new TestModel("data2", 42);
        TestModel data3 = new TestModel("data3", 43);
        repository.save(data1);
        repository.save(data2);
        repository.save(data3);
        Collection<TestModel> read = repository.getAll();
        Assert.assertThat(read, Matchers.containsInAnyOrder(data1, data2, data3));
        repository.delete(data2.getId());
        read = repository.getAll();
        Assert.assertThat(read, Matchers.containsInAnyOrder(data1, data3));
    }

    @Test
    public void saveAndRead() {
        TestModel data1 = new TestModel("data1", 41);
        TestModel data2 = new TestModel("data2", 42);
        TestModel data3 = new TestModel("data3", 43);
        repository.save(data1);
        Assert.assertThat(data1.getId(), Matchers.not(0L));
        repository.save(data2);
        Assert.assertThat(data2.getId(), Matchers.not(0L));
        repository.save(data3);
        Assert.assertThat(data3.getId(), Matchers.not(0L));
        Collection<TestModel> read = repository.getAll();
        Assert.assertThat(read, Matchers.containsInAnyOrder(data1, data2, data3));
    }

    @Test
    public void saveAndFind() {
        TestModel data1 = new TestModel("data1", 41);
        repository.save(data1);
        Assert.assertThat(data1.getId(), Matchers.not(0L));
        TestModel find = repository.find(data1.getId());
        Assert.assertThat(find, Matchers.is(data1));
    }

    private static class TestModel extends BaseModel<TestModel> {
        String data;
        int number;

        TestModel(String data, int number) {
            this.data = data;
            this.number = number;
        }

        public TestModel() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestModel testModel = (TestModel) o;
            return number == testModel.number &&
                    Objects.equals(data, testModel.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, number);
        }
    }
}
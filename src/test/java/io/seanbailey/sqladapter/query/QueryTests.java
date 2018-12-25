package io.seanbailey.sqladapter.query;

import static org.junit.Assert.assertEquals;

import io.seanbailey.sqladapter.Model;
import io.seanbailey.sqladapter.TestModel;
import org.junit.Test;

/**
 * Tests the SQL generated by various different chain functions.
 */
public class QueryTests {

  @Test
  public void testAll() {
    String sql = Model.all(TestModel.class).toString();
    assertEquals("SELECT * FROM _;", sql);
  }

  @Test
  public void testAllCount() {
    String sql = Model.all(TestModel.class).count().toString();
    assertEquals("SELECT COUNT(*) FROM _;", sql);
  }

  @Test
  public void testAllExists() {
    String sql = Model.all(TestModel.class).exists().toString();
    assertEquals("SELECT COUNT(*) FROM _;", sql);
  }

  @Test
  public void testLimit() {
    Query query = Model.all(TestModel.class).limit(5);
    assertEquals("SELECT * FROM _ LIMIT 5;", query.toString());

    query.limit();
    assertEquals("SELECT * FROM _;", query.toString());
  }

  @Test
  public void testOffset() {
    Query query = Model.all(TestModel.class).offset(3);
    assertEquals("SELECT * FROM _ OFFSET 3;", query.toString());

    query.offset();
    assertEquals("SELECT * FROM _;", query.toString());

    query.offset(-5);
    assertEquals("SELECT * FROM _;", query.toString());
  }

  @Test
  public void testPer() {
    Query query = Model.all(TestModel.class).per(5);
    assertEquals("SELECT * FROM _ LIMIT 5;", query.toString());

    query.per();
    assertEquals("SELECT * FROM _;", query.toString());

    query.per(-5);
    assertEquals("SELECT * FROM _;", query.toString());
  }

  @Test
  public void testPageWithoutLimit() {
    Query query = Model.all(TestModel.class).page(5);
    assertEquals("SELECT * FROM _;", query.toString());
  }

  @Test
  public void testPageWithLimit() {
    Query query = Model.all(TestModel.class).per(5).page(1);
    assertEquals("SELECT * FROM _ LIMIT 5 OFFSET 0;", query.toString());

    query.page(3);
    assertEquals("SELECT * FROM _ LIMIT 5 OFFSET 10;", query.toString());

    query.page(0);
    assertEquals("SELECT * FROM _ LIMIT 5 OFFSET 0;", query.toString());
  }

  @Test
  public void testWhere() {
    Query query = Model.where(TestModel.class, "title", "test");
    assertEquals("SELECT * FROM _ WHERE title = \"test\";", query.toString());

    query.where("author", "Sean Bailey");
    assertEquals("SELECT * FROM _ WHERE title = \"test\" AND author = \"Sean Bailey\";", query.toString());
  }

  @Test
  public void testOr() {
    Query query = Model.where(TestModel.class, "title", "test").or("author", "Sean Bailey");
    assertEquals("SELECT * FROM _ WHERE title = \"test\" OR author = \"Sean Bailey\";", query.toString());
  }

  @Test
  public void testFind() {
    Query query = Model.find(TestModel.class, "title", "test");
    assertEquals("SELECT * FROM _ WHERE title = \"test\" LIMIT 1;", query.toString());
  }

  @Test
  public void testOrder() {
    Query query = Model.all(TestModel.class).order("title", Query.Order.ASCENDING);
    assertEquals("SELECT * FROM _ ORDER BY title ASC;", query.toString());

    query.reorder("title");
    assertEquals("SELECT * FROM _ ORDER BY title ASC;", query.toString());

    query.order("author", Query.Order.DESCENDING);
    assertEquals("SELECT * FROM _ ORDER BY title ASC, author DESC;", query.toString());

    query.order();
    assertEquals("SELECT * FROM _;", query.toString());
  }
}
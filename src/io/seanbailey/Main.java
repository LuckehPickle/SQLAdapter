package io.seanbailey;

import io.seanbailey.model.User;
import io.seanbailey.sql.util.SQLUtil;

public class Main {

  public static void main(String[] args) {
    System.out.println(SQLUtil.getTableName(User.class));
  }

}

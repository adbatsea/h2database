/*
 * Copyright 2004-2019 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.schema;

import org.h2.engine.DbObject;

/**
 * 任何存储在模式中的数据库对象。
 */
public interface SchemaObject extends DbObject {

    /**
     * 获取这个对象的模式
     *
     * @return 模式
     */
    Schema getSchema();

    /**
     * 检查这是否是一个不在元数据中、脚本中存在，并且不会被DROP ALL OBJECTS语句删除
     * 的隐藏对象。
     *
     * @return 如果是隐藏对象则为true
     */
    boolean isHidden();

}

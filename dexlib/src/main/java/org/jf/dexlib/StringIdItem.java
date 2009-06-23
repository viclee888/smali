/*
 * [The "BSD licence"]
 * Copyright (c) 2009 Ben Gruver
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jf.dexlib;

import org.jf.dexlib.Util.Utf8Utils;

public class StringIdItem extends IndexedItem<StringIdItem> {
    private final OffsettedItemReference<StringDataItem> stringDataReferenceField;

    public StringIdItem(DexFile dexFile, int index) {
        super(dexFile, index);
        fields = new Field[] {
                stringDataReferenceField = new OffsettedItemReference<StringDataItem>(dexFile.StringDataSection,
                        new IntegerField(null), "string_data_off")
        };
    }

    public StringIdItem(DexFile dexFile, StringDataItem stringDataItem) {
        this(dexFile, -1);
        stringDataReferenceField.setReference(stringDataItem);
    }

    public StringIdItem(DexFile dexFile, String value) {
        this(dexFile, new StringDataItem(dexFile, value));
    }

    protected int getAlignment() {
        return 4;
    }

    public ItemType getItemType() {
        return ItemType.TYPE_STRING_ID_ITEM;
    }

    public String getConciseIdentity() {
        return "string_id_item: " + Utf8Utils.escapeString(getStringValue());
    }

    public String getStringValue() {
        return stringDataReferenceField.getReference().getStringValue();
    }

    public StringDataItem getStringData() {
        return stringDataReferenceField.getReference();
    }

    public int compareTo(StringIdItem o) {
        //sort by the string value
        return getStringValue().compareTo(o.getStringValue());
    }
}

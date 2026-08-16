/*
 *	Mark's Code Fractal CFLib DbUtil 3.1 Common Library
 *
 *	Copyright 2016-2026 Mark Stephen Sobkow
 *
 *	CFLib DbUtil provides the essential database types and utilities for Code Fractal applications.
 *
 *	These files are part of Mark's Code Fractal CFLib DbUtil.
 *
 *	Mark's Code Fractal CFLib DbUtil is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later with static linking exception.
 *
 *	As a special exception, Mark Sobkow gives you permission to link this library
 *	with independent modules to produce an executable, provided that none of them
 *	conflict with the intent of the LGPLv3; that is, you are not allowed to invoke
 *	the methods of this library from non-LGPLv3-compatibly licensed code.  That said,
 *	code which does not rely on this library is free to specify whatever license its
 *	authors decide to use. Mark Sobkow specifically rejects the infectious nature of
 *	the LGPLv3, and considers the mere act of including LGPLv3 modules in an
 *	executable to be perfectly reasonable given tools like modern Java's single-jar
 *	deployment options.
 *
 *	Mark's Code Fractal CFLib DbUtil is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Mark's Code Fractal CFLib DbUtil is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU Library General Public License for more details.
 *
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFLib DbUtil.  If not, see &lt;https://www.gnu.org/licenses/&gt;.
 *
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 */

package server.markhome.mcf.v3_1.cflib.dbutil;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.keyhash.*;

/**
 *
 * @author msobkow
 */
@Embeddable
public class CFLibDbKeyHash128 extends CFLibDbKeyHashBase<CFLibDbKeyHash128> implements Serializable, ICFLibKeyHash128  {
  static final long serialVersionUID = 202608160552L;

  @Override
  public int getHashLength() {
    return HASH_LENGTH;
  }

  @Override
  public int getHashLengthString() {
    return HASH_LENGTH_STRING;
  }

  @Override
  public String getHashAlgo() {
    return HASH_ALGO;
  }

  static MessageDigest[] m = null;
  @Override
  protected MessageDigest[] getM() {
    return m;
  }

  @Override
  public void initStatics() {
    if (m != null) {
      return;
    }
    super.initStatics();
    try {
      m = new MessageDigest[CONCURRENT_DIGESTS];
      for (int i = 0; i < CONCURRENT_DIGESTS; i++) {
        m[i] = MessageDigest.getInstance(HASH_ALGO);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Column(name = "bytes", nullable = false)
  protected byte[] bytes;

  public static byte[] sbytesFromHex(String string) {
    if (string == null) {
      // allowed
    }
    else if (string.length() > HASH_LENGTH * 2) {
      throw new IllegalArgumentException("string length is " + string.length() + ".  Must be <= " + HASH_LENGTH * 2 + ".  string is '" + string + "'.");
    }
    byte[] b = new byte[HASH_LENGTH];
    if (string == null) {
      return b;
    }

    int n = string.length();
    for (int i = 0; i < n - 1; i += 2) {
      b[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i + 1), 16));
    }
    return b;
  }

  public static CFLibDbKeyHash128 fromHex(String string) {
    byte[] b = sbytesFromHex(string);
    CFLibDbKeyHash128 h = new CFLibDbKeyHash128();
    h.bytes = b;
    return h;
  }

  public static Comparator<ICFLibKeyHash128> getComparator() {

    return new Comparator<ICFLibKeyHash128>() {
      @Override
      public int compare(ICFLibKeyHash128 a, ICFLibKeyHash128 b) {
        return compareOrdered(a, b);
      }
    };
  }

  public CFLibDbKeyHash128() {
  }

  /**
   * This is the hex code of the underlying ID. THIS IS NOT A HASHING FUNCTION.
   */
  public CFLibDbKeyHash128(String hexId) {
    super(hexId);
  }

  public CFLibDbKeyHash128(byte[] anId) {
    super(anId);
  }

  public CFLibDbKeyHash128(ICFLibKeyHash128 otherKey) {
	bytes = new byte[HASH_LENGTH];
	if(otherKey != null) {
		System.arraycopy(otherKey.getBytes(), 0, bytes, 0, HASH_LENGTH);
	}
  }

  public CFLibDbKeyHash128(ICFLibKeyHash160 k) {
    bytes = new byte[HASH_LENGTH];
    if (k != null) {
      System.arraycopy(k.getBytes(), 0, bytes, 0, HASH_LENGTH);
    }
  }

  public CFLibDbKeyHash128(ICFLibKeyHash224 k) {
    bytes = new byte[HASH_LENGTH];
    if (k != null) {
      System.arraycopy(k.getBytes(), 0, bytes, 0, HASH_LENGTH);
    }
  }

  public CFLibDbKeyHash128(ICFLibKeyHash256 k) {
    bytes = new byte[HASH_LENGTH];
    if (k != null) {
      System.arraycopy(k.getBytes(), 0, bytes, 0, HASH_LENGTH);
    }
  }

  public CFLibDbKeyHash128(ICFLibKeyHash384 k) {
    bytes = new byte[HASH_LENGTH];
    if (k != null) {
      System.arraycopy(k.getBytes(), 0, bytes, 0, HASH_LENGTH);
    }
  }

  public CFLibDbKeyHash128(ICFLibKeyHash512 k) {
    bytes = new byte[HASH_LENGTH];
    if (k != null) {
      System.arraycopy(k.getBytes(), 0, bytes, 0, HASH_LENGTH);
    }
  }

  public static CFLibDbKeyHash128 fromInt(int v) {
    CFLibDbKeyHash128 h = nullGet();
    h.bytes[3] = (byte) (v & 0xFF);
    h.bytes[2] = (byte) ((v >> 8) & 0xFF);
    h.bytes[1] = (byte) ((v >> 16) & 0xFF);
    h.bytes[0] = (byte) ((v >> 24) & 0xFF);
    return h;
  }

  public CFLibDbKeyHash128(int notUsed) {
    super(notUsed);
  }

  public static final boolean isNull(CFLibDbKeyHash128 anId) {
    return anId == null || anId.isNull();
  }

  public byte[] getBytes() {
    return bytes;
  }

  /**
   * Get a new hash object with the key set to all 0s
   */
  static public CFLibDbKeyHash128 nullGet() {
    CFLibDbKeyHash128 k = new CFLibDbKeyHash128(new byte[HASH_LENGTH]);
    return k;
  }

  static public String getNullString() {
    return "00000000000000000000000000000000";
  }

  /**
   * We want DbKeyHashXX to be immutable so this method shouldn't even exist;
   * however it is necessary for JPA. This is the only time it should be used.
   *
   * @param newBytes to be copied from.
   */
  @Override
  public void setBytes(byte[] newBytes) {
    if (newBytes == null) {
      throw new NullPointerException("newBytes must not be null.");
    }
    if (newBytes.length != HASH_LENGTH) {
      throw new IllegalArgumentException("newBytes must be of length " + HASH_LENGTH + ".");
    }
    bytes = newBytes.clone();
  }

  /** Copy into existing key */
  @Override
  public void setBytes(byte[] newBytes, int offset,  int length) {
      System.arraycopy(newBytes, offset, bytes, 0, Math.min(HASH_LENGTH,length));
  }

  static public int compareOrdered(ICFLibKeyHash128 h1, ICFLibKeyHash128 h2) {
    if (h1 == null) {
      if (h2 == null) {
        return 0;
      }
      else {
        return -1;
      }
    }
    else {
      if (h2 == null) {
        return 1;
      }
      else {
        for (int i = 0; i < HASH_LENGTH; i++) {
          int v1 = h1.getBytes()[i] + 256;
          int v2 = h2.getBytes()[i] + 256; if (v1 < v2) return -1;
          if (v1 > v2) return 1;
        }
      }
    }
    return 0;
  }

  public static CFLibDbKeyHash128 hash(String text) {
    if (text != null) {
      try {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
        byte[] buf = text.getBytes("UTF-8");
        md.update(buf);

        return new CFLibDbKeyHash128(md.digest());
      }
      catch (Exception ex) {
      }
    }
    return new CFLibDbKeyHash128(0);
  }

  public static CFLibDbKeyHash128 hash(byte[] payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      md.update(payload);

      return new CFLibDbKeyHash128(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash128(0);
  }

  public static CFLibDbKeyHash128 hash(byte[]... payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      for (byte[] bs : payload) {
        md.update(bs);
      }

      return new CFLibDbKeyHash128(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash128(0);
  }

  public static CFLibDbKeyHash128 hash(ICFLibKeyHash128... payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      for (ICFLibKeyHash128 k : payload) {
        md.update(k.getBytes());
      }
      return new CFLibDbKeyHash128(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash128(0);
  }

  public static CFLibDbKeyHash128 hash(int[] payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      for (int x : payload) {
        md.update((byte) ((x >>> 24) & 255));
        md.update((byte) ((x >>> 16) & 255));
        md.update((byte) ((x >>> 8) & 255));
        md.update((byte) (x & 255));
      }

      return new CFLibDbKeyHash128(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash128(0);
  }

  @Override
  public CFLibDbKeyHash128 deepClone() {
    return new CFLibDbKeyHash128(this);
  }

  static public CFLibDbKeyHash128 fromHexQuick(String string) {
    if (string == null) {
      return null;
    }
    if (string.length() != HASH_LENGTH * 2) {
      return null;
    }
    for (int i = 0; i < string.length(); i++) {
      try {
        if (Character.digit(string.charAt(i), 16) < 0) {
          return null;
        }
      }
      catch (Exception e) {
        return null;
      }

    }
    try {
      return fromHex(string);
    }
    catch (Exception e) {
      return null;
    }
  }

  public static final CFLibDbKeyHash128[] toCFLibDbKeyHash128(String[] ids) {
    if (ids == null) {
      return null;
    }
    if (ids.length == 0) {
      return new CFLibDbKeyHash128[0];
    }
    CFLibDbKeyHash128[] r = new CFLibDbKeyHash128[ids.length];
    for (int i = 0; i < ids.length; i++) {
      r[i] = new CFLibDbKeyHash128(ids[i]);
    }
    return r;
  }

  public static final List<CFLibDbKeyHash128> toCFLibDbKeyHash128List(String[] ids) {

    if (ids == null) {
      return null;
    }
    if (ids.length == 0) {
      return Collections.emptyList();
    }
    List<CFLibDbKeyHash128> r = new ArrayList<CFLibDbKeyHash128>(ids.length);
    for (int i = 0; i < ids.length; i++) {
      r.add(new CFLibDbKeyHash128(ids[i]));
    }
    return r;

  }

  public static final Set<CFLibDbKeyHash128> toCFLibDbKeyHash128Set(String[] ids) {

    if (ids == null) {
      return null;
    }
    if (ids.length == 0) {
      return Collections.emptySet();
    }
    Set<CFLibDbKeyHash128> r = new HashSet<CFLibDbKeyHash128>(ids.length);
    for (int i = 0; i < ids.length; i++) {
      r.add(new CFLibDbKeyHash128(ids[i]));
    }
    return r;

  }
}

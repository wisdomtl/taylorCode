package test.taylor.com.taylorcode.kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.constraint_layout_activity.*
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.util.ofMap
import test.taylor.com.taylorcode.util.print
import test.taylor.com.taylorcode.util.splitByDigit
import test.taylor.com.taylorcode.util.subDigit
import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class KotlinActivity : AppCompatActivity() {
    val list1 = listOf<String>("abd", "add", "fff")
    val list2 = listOf<String>("abd", "add", "fff")

    val ids = "5e4f88da118778ee8c233e20\n" +
            "5e537e53118778ee8cc14596\n" +
            "5e5493fb118778ee8ceb34f9\n" +
            "5e57315f118778ee8c538367\n" +
            "5e57843f118778ee8c61b768\n" +
            "5e5785ed118778ee8c61ffb1\n" +
            "5e58afe3118778ee8c90a9ec\n" +
            "5e58b303118778ee8c912c4a\n" +
            "5e58b46e118778ee8c9167e8\n" +
            "5e8da76c118778ee8c6f3b20\n" +
            "5e8dabab118778ee8c70090b\n" +
            "5e8dace9118778ee8c704712\n" +
            "5e8ecc03118778ee8ca1623b\n" +
            "5e8fea0c118778ee8cd2e2c2\n" +
            "5e8feba5118778ee8cd33010\n" +
            "5e8fec75118778ee8cd3552c\n" +
            "5e8fedc3118778ee8cd398bb\n" +
            "5e8fee0b118778ee8cd3a57b\n" +
            "5e8fee46118778ee8cd3b0f9\n" +
            "5e901c94118778ee8cdc2c66\n" +
            "5e9590db118778ee8cd08f96\n" +
            "5e9590ea118778ee8cd09296\n" +
            "5e9590ed118778ee8cd09331\n" +
            "5e959234118778ee8cd0d013\n" +
            "5e97db33118778ee8c34eef5\n" +
            "5e97db39118778ee8c34f1d8\n" +
            "5e97db41118778ee8c34f4bf\n" +
            "5e992cfc118778ee8c747ba9\n" +
            "5e9d6aad118778ee8c309593\n" +
            "5e9ec6f0118778ee8c6c128d\n" +
            "5e9ec6fc118778ee8c6c147b\n" +
            "5e9ec703118778ee8c6c1593\n" +
            "5ea13bf9118778ee8cd7e388\n" +
            "5ea13bff118778ee8cd7e4c4\n" +
            "5ea13c0b118778ee8cd7e6e6\n" +
            "5ea13c12118778ee8cd7e85b\n" +
            "5ea13c1a118778ee8cd7ea06\n" +
            "5ea13c49118778ee8cd7f4d7\n" +
            "5ea55ca7118778ee8c8c0000\n" +
            "5ea55cad118778ee8c8c010d\n" +
            "5ea55ccf118778ee8c8c07db\n" +
            "5ea55ce4118778ee8c8c0be4\n" +
            "5ea7c955118778ee8cee6969\n" +
            "5ea7cdbb118778ee8cefe28d\n" +
            "5ebb5a33118778ee8c3d71a8\n" +
            "5ebe52e8118778ee8cb9adf0\n" +
            "5ebe52f2118778ee8cb9affc\n" +
            "5ebe52fb118778ee8cb9b1dd\n" +
            "5ebe534b118778ee8cb9c0e0\n" +
            "5edef524118778ee8c465cc9\n" +
            "5edef5fb118778ee8c467fe8\n" +
            "5edef64a118778ee8c468c87\n" +
            "5ee1a737118778ee8cb2d123\n" +
            "5efaa7a60d5f3884be7f26bb\n" +
            "5efaa7dc0d5f3884be7f26bc\n" +
            "5efeda45e5b8b8f0ef4ec796\n" +
            "5f02994e118778ee8c2d0756\n" +
            "5f0d5b1f118778ee8c08651b\n" +
            "5f196c50abbd214061312934\n" +
            "5f20dd16118778ee8c6a8cdd\n" +
            "5f223641118778ee8ca94a56\n" +
            "5f2bbf957cf60f3f8f5ec6eb\n" +
            "5f2bc37d7cf60f3f8f5ec6ec\n" +
            "5f2d044a118778ee8c88e74f\n" +
            "5f2d16f07cf60f3f8f5ec786\n" +
            "5f324bf6118778ee8c744162\n" +
            "5f324c17118778ee8c7449dc\n" +
            "5f35fe9a7cf60f3f8f5eca0c\n" +
            "5f35feda7cf60f3f8f5eca0d\n" +
            "5f365dfe7daca80012a0d6f0\n" +
            "5f365fc8be7ceae66029de06\n" +
            "5f39f53cf37de293edf44bca\n" +
            "5f39f5c1f37de293edf44bcb\n" +
            "5f39f826f37de293edf44bcc\n" +
            "5f39fe87f37de293edf44bd2\n" +
            "5f39ffe3f37de293edf44bd3\n" +
            "5f3a2edbf37de293edf44bea\n" +
            "5f3a3078f37de293edf44beb\n" +
            "5f3a3648f37de293edf44bf4\n" +
            "5f3a3b60f37de293edf44bf5\n" +
            "5f3a3daef37de293edf44bf6\n" +
            "5f3a3e18750939dcefe08acd\n" +
            "5f3a504225faccfea7542da2\n" +
            "5f3a50ae25faccfea7542da3\n" +
            "5f3a552c25faccfea7542da5\n" +
            "5f3b50f8f106eaa9de30dfe1\n" +
            "5f3b526bf106eaa9de30dfe2\n" +
            "5f3bbb0e932f1adbade808c1\n" +
            "5f3bce28932f1adbade808d5\n" +
            "5f3bcf7e932f1adbade808d6\n" +
            "5f3bd0ae932f1adbade808d9\n" +
            "5f3cc856932f1adbade80945\n" +
            "5f3cd967932f1adbade8097d\n" +
            "5f3e39227eefd5f3389306f5\n" +
            "5f51f9805bed638c91b032bf\n" +
            "5f51ffb84fd5c43d443cb98e\n" +
            "5f5207494fd5c43d443cb990\n" +
            "5f520b524fd5c43d443cb991\n" +
            "5f587f7680f02b236ca04269\n" +
            "5f62db720cc901fd6de929fa\n" +
            "5f6316500cc901fd6de929fd\n" +
            "5f646079a8ab468dbc3617e7\n" +
            "5f646a66a8ab468dbc3617eb\n" +
            "5f68657dcee9409153c0ccad\n" +
            "5f687704cee9409153c0ccb1\n" +
            "5f6879d1cee9409153c0ccb5\n" +
            "5f687d19cee9409153c0ccb6\n" +
            "5f6880f8cee9409153c0ccbc\n" +
            "5f69977acee9409153c0ccce\n" +
            "5f69a99bcee9409153c0ccd1\n" +
            "5f69aa0fcee9409153c0ccd3\n" +
            "5f6ab366cee9409153c0ccf6\n" +
            "5f6b3174099b98e690e57603\n" +
            "5f6c05fd099b98e690e57614\n" +
            "5f6c0dcf099b98e690e5761a\n" +
            "5f6c1929099b98e690e57623\n" +
            "5f6c3ae5099b98e690e57628\n" +
            "5f6c3d82099b98e690e5762d\n" +
            "5f6c48dd099b98e690e57636\n" +
            "5f6c53c6099b98e690e57640\n" +
            "5f6c5b80099b98e690e57649\n" +
            "5f6c5d1e099b98e690e5764b\n" +
            "5f6d55ea099b98e690e5767c\n" +
            "5f6d6b7f099b98e690e5769a\n" +
            "5f6da65a099b98e690e576ac\n" +
            "5f6dc4c4099b98e690e576c6\n" +
            "5f6dcfc8099b98e690e576ca\n" +
            "5f6eb714099b98e690e576e6\n" +
            "5f6ef4c2099b98e690e57713\n" +
            "5f703f49099b98e690e57772\n" +
            "5f706dcb099b98e690e577a9\n" +
            "5f71b0e0099b98e690e577ba\n" +
            "5f81234ecacaf7bd6d7f76ed\n" +
            "5f812368cacaf7bd6d7f76ee\n" +
            "5f83fc81118778ee8c584aab\n" +
            "5f840680118778ee8c5a8310\n" +
            "5f8406fa118778ee8c5a9df8\n" +
            "5f840713118778ee8c5aa38f\n" +
            "5f84080a118778ee8c5adc21\n" +
            "5f855bc6118778ee8ca39eb5\n" +
            "5f8567e9118778ee8ca64cfb\n" +
            "5f8573a4118778ee8ca8e48a\n" +
            "5f8577be118778ee8ca9c876\n" +
            "5f857b01118778ee8caa80d9\n" +
            "5f858320118778ee8cac41de\n" +
            "5f8587a1118778ee8cad4530\n" +
            "5f8588ec118778ee8cad8f22\n" +
            "5f86965a118778ee8ce6fbb3\n" +
            "5f8696b5118778ee8ce71220\n" +
            "5f8698ec118778ee8ce78dde\n" +
            "5f869938118778ee8ce79e59\n" +
            "5f86aa24118778ee8ceb62a7\n" +
            "5f86ad1001453ae820c7afde\n" +
            "5f86b8e901453ae820c7aff4\n" +
            "5f86d62d118778ee8cf5c88d\n" +
            "5f86d688118778ee8cf5ddb7\n" +
            "5f86d6a0118778ee8cf5e325\n" +
            "5f86d81f118778ee8cf63cc5\n" +
            "5f86d860118778ee8cf64bbf\n" +
            "5f86d8f9118778ee8cf66ef5\n" +
            "5f86d96b118778ee8cf68992\n" +
            "5f86d9d6118778ee8cf6a2cb\n" +
            "5f86d9e4118778ee8cf6a602\n" +
            "5f86d9ef118778ee8cf6a8c9\n" +
            "5f86da48118778ee8cf6bfa6\n" +
            "5f86da57118778ee8cf6c339\n" +
            "5f87ad91118778ee8c242530\n" +
            "5f87ef7d118778ee8c332e81\n" +
            "5f87f311118778ee8c3408b2\n" +
            "5f89688c118778ee8c834b61\n" +
            "5f8968a9118778ee8c8350b1\n" +
            "5f896c29118778ee8c83fdc4\n" +
            "5f8d3708118778ee8c28bff7\n" +
            "5f8d59b2118778ee8c2f42ae\n" +
            "5f8d5a83118778ee8c2f6bef\n" +
            "5f8d7291118778ee8c33bd1e\n" +
            "5f8d731a118778ee8c33d6ec\n" +
            "5f8d73e0118778ee8c33fa27\n" +
            "5f8d748b118778ee8c341956\n" +
            "5f8e63a2118778ee8c5dac4f\n" +
            "5f8e6445118778ee8c5dca43\n" +
            "5f992f65118778ee8c4b979d\n" +
            "5f992faa118778ee8c4ba44b\n" +
            "5f9b78d3118778ee8cb202ab\n" +
            "5f9b78e0118778ee8cb20523\n" +
            "5f9b78ee118778ee8cb207c9\n" +
            "5f9b78fc118778ee8cb20ac8\n" +
            "5f9b7908118778ee8cb20d8d\n" +
            "5f9b79a7118778ee8cb22b5a\n" +
            "5f9b79b5118778ee8cb22df0\n" +
            "5f9b79bd118778ee8cb22f8d\n" +
            "5f9b7a2a118778ee8cb243d4\n" +
            "5f9b7b7f118778ee8cb28494\n" +
            "5f9b7ba1118778ee8cb28b1e\n" +
            "5f9b7c57118778ee8cb2aeb8\n" +
            "5f9b7c61118778ee8cb2b099\n" +
            "5f9b7cb8118778ee8cb2c086\n" +
            "5f9b7cc8118778ee8cb2c3ae\n" +
            "5f9b7cd2118778ee8cb2c575\n" +
            "5f9b7d49118778ee8cb2db7f\n" +
            "5f9b7d53118778ee8cb2dd51\n" +
            "5f9b7d6a118778ee8cb2e204\n" +
            "5f9bd469118778ee8cc40c2e\n" +
            "5fa221df6631d96fa7959b1c\n" +
            "5fa25fb76631d96fa7959b1d\n" +
            "5fa262956631d96fa7959b1e\n" +
            "5fa26da86631d96fa7959b20\n" +
            "5fa26dcd6631d96fa7959b21\n" +
            "5fa272eb6631d96fa7959b23\n" +
            "5fa27c546631d96fa7959b24\n" +
            "5fa28198ca10c6cff59282cb\n" +
            "5fa28388ca10c6cff59282cd\n" +
            "5fa285cfca10c6cff59282ce\n" +
            "5fa2860bca10c6cff59282cf\n" +
            "5fa28694ca10c6cff59282d0\n" +
            "5fa2887dca10c6cff59282d1\n" +
            "5fa288fdca10c6cff59282d2\n" +
            "5fa28c9aca10c6cff59282d3\n" +
            "5fa290dfca10c6cff59282d4\n" +
            "5fa35ce6ca10c6cff59282d8\n" +
            "5fa3602fca10c6cff59282d9\n" +
            "5fa361cbca10c6cff59282da\n" +
            "5fa3625aca10c6cff59282db\n" +
            "5fa362e0ca10c6cff59282dc\n" +
            "5fa367d8ca10c6cff59282dd\n" +
            "5fa36c63ca10c6cff59282de\n" +
            "5fa370e6ca10c6cff59282df\n" +
            "5fa3713dca10c6cff59282e0\n" +
            "5fa372ddca10c6cff59282e1\n" +
            "5fa375b8ca10c6cff59282e2\n" +
            "5fa375e6ca10c6cff59282e3\n" +
            "5fa8a2b7ca10c6cff59282ea\n" +
            "5fa8a394ca10c6cff59282eb\n" +
            "5fa8a471ca10c6cff59282ec\n" +
            "5fa8a4d6ca10c6cff59282ed\n" +
            "5fa8a808ca10c6cff59282ee\n" +
            "5fa8a882ca10c6cff59282ef\n" +
            "5fa8a971ca10c6cff59282f0\n" +
            "5fa9f131ca10c6cff59282f2\n" +
            "5fa9f178ca10c6cff59282f3\n" +
            "5fa9f1f9ca10c6cff59282f4\n" +
            "5fa9f270ca10c6cff59282f5\n" +
            "5fa9f32fca10c6cff59282f6\n" +
            "5fa9f3dfca10c6cff59282f7\n" +
            "5fa9f460ca10c6cff59282f8\n" +
            "5fa9f4baca10c6cff59282f9\n" +
            "5fa9f537ca10c6cff59282fa\n" +
            "5fa9f831ca10c6cff59282fb\n" +
            "5fa9f901ca10c6cff59282ff\n" +
            "5fa9f9caca10c6cff5928300\n" +
            "5faa4fc3ca10c6cff5928301\n" +
            "5fac9c8cca10c6cff592830b\n" +
            "5fadef8fca10c6cff592832c\n" +
            "5fb23fdeaf62eb2e40feba02\n" +
            "5fb7b959e92c2b7e9ba33209\n" +
            "5fbb210fe92c2b7e9ba33220\n" +
            "5fbb30d8e92c2b7e9ba33229\n" +
            "5fbb5927bd9cb31f9f024c4d\n" +
            "5fbb6025f22a1e4b7f4e0e37\n" +
            "5fbb83718e3bb3336614d34f\n" +
            "5fbb8a708e3bb3336614d355\n" +
            "5fbb91648e3bb3336614d356\n" +
            "5fbcc1ca8e3bb3336614d390\n" +
            "5fbcc2dd8e3bb3336614d392\n" +
            "5fbcc3f58e3bb3336614d393\n" +
            "5fbccdc48e3bb3336614d39d\n" +
            "5fbccf928e3bb3336614d39f\n" +
            "5fbcd6a08e3bb3336614d3a1\n" +
            "5fbce36f8e3bb3336614d3a9\n" +
            "5fbcef968e3bb3336614d3b1\n" +
            "5fbcf0868e3bb3336614d3b2\n" +
            "5fbcf0fe8e3bb3336614d3b3\n" +
            "5fbcf1f48e3bb3336614d3b4\n" +
            "5fbcf3088e3bb3336614d3b5\n" +
            "5fbcf41f8e3bb3336614d3b6\n" +
            "5fbcf4f48e3bb3336614d3b7\n" +
            "5fbcf5938e3bb3336614d3b8\n" +
            "5fbcf5ab8e3bb3336614d3b9\n" +
            "5fbcf6328e3bb3336614d3ba\n" +
            "5fbcf80c8e3bb3336614d3bb\n" +
            "5fbcf87e8e3bb3336614d3bc\n" +
            "5fbd05c18e3bb3336614d3bd\n" +
            "5fbd0ecb8e3bb3336614d3be\n" +
            "5fbd106c8e3bb3336614d3bf\n" +
            "5fbd11898e3bb3336614d3c0\n" +
            "5fbd127c8e3bb3336614d3c1\n" +
            "5fbd17328e3bb3336614d3c2\n" +
            "5fbd17f28e3bb3336614d3c3\n" +
            "5fbdb80f8e3bb3336614d3c4\n" +
            "5fbdc4618e3bb3336614d3c6\n" +
            "5fbdc5ca8e3bb3336614d3c7\n" +
            "5fbdcda98e3bb3336614d3cf\n" +
            "5fbdcfaf4c5897d20d957dc4\n" +
            "5fbdcfe64c5897d20d957dc6\n" +
            "5fbdd4aa9cfb81c8f1893335\n" +
            "5fbdd68e9cfb81c8f1893336\n" +
            "5fbdfed09cfb81c8f1893341\n" +
            "5fbe01f99cfb81c8f1893346\n" +
            "5fbe02bb9cfb81c8f1893347\n" +
            "5fbe072c9cfb81c8f189334b\n" +
            "5fbe09489cfb81c8f189334c\n" +
            "5fbe0b3d9cfb81c8f1893351\n" +
            "5fbe0cfa9cfb81c8f1893354\n" +
            "5fbe0d219cfb81c8f1893356\n" +
            "5fbe10f39cfb81c8f189335d\n" +
            "5fbe12899cfb81c8f1893360\n" +
            "5fbe16b59cfb81c8f1893363\n" +
            "5fbe1fca9cfb81c8f189336f\n" +
            "5fbe201e9cfb81c8f1893370\n" +
            "5fbe27939cfb81c8f1893379\n" +
            "5fbe29379cfb81c8f189337c\n" +
            "5fbe2acf9cfb81c8f189337e\n" +
            "5fbe2b749cfb81c8f189337f\n" +
            "5fbe2cfd9cfb81c8f1893380\n" +
            "5fbe2dc7118778ee8cd41350\n" +
            "5fbe37989cfb81c8f1893384\n" +
            "5fbf1b95da8f8f6e2ee01f5e\n" +
            "5fbf1d1eda8f8f6e2ee01f60\n" +
            "5fbf1eadda8f8f6e2ee01f62\n" +
            "5fbf1f4cda8f8f6e2ee01f63\n" +
            "5fbf22c4da8f8f6e2ee01f66\n" +
            "5fbf23bbda8f8f6e2ee01f68\n" +
            "5fbf243a118778ee8cfe80c8\n" +
            "5fbf26bdda8f8f6e2ee01f6a\n" +
            "5fbf2966da8f8f6e2ee01f6b\n" +
            "5fbf2a9ada8f8f6e2ee01f6c\n" +
            "5fbf2b84da8f8f6e2ee01f6d\n" +
            "5fbf44b4da8f8f6e2ee01f6f\n" +
            "5fbf45feda8f8f6e2ee01f71\n" +
            "5fbf46f4da8f8f6e2ee01f72\n" +
            "5fbf481ada8f8f6e2ee01f73\n" +
            "5fbf4970da8f8f6e2ee01f74\n" +
            "5fbf49fada8f8f6e2ee01f75\n" +
            "5fbf4b26da8f8f6e2ee01f76\n" +
            "5fbf4bb0da8f8f6e2ee01f78\n" +
            "5fbf4eb0da8f8f6e2ee01f7a\n" +
            "5fbf5042da8f8f6e2ee01f7b\n" +
            "5fbf522eda8f8f6e2ee01f7d\n" +
            "5fbf5366da8f8f6e2ee01f7e\n" +
            "5fbf564dda8f8f6e2ee01f80\n" +
            "5fbf62e7992cc77edc1a580d\n" +
            "5fbf6359992cc77edc1a580e\n" +
            "5fbf6447992cc77edc1a580f\n" +
            "5fbf66ec992cc77edc1a5814\n" +
            "5fbf67ac992cc77edc1a5815\n" +
            "5fbf68a5992cc77edc1a5816\n" +
            "5fbf7fdd992cc77edc1a581a\n" +
            "5fbf91fdc8b8a647b0389fb1\n" +
            "5fbf9278c8b8a647b0389fb2\n" +
            "5fbf934dc8b8a647b0389fb3\n" +
            "5fbf9437c8b8a647b0389fb4\n" +
            "5fbf94e2c8b8a647b0389fb5\n" +
            "5fbf95a5c8b8a647b0389fb6\n" +
            "5fbf9812c8b8a647b0389fb7\n" +
            "5fc0bd7d9c9a2bfac2a19c4f\n" +
            "5fc459259c9a2bfac2a19c59\n" +
            "5fc459f19c9a2bfac2a19c5a\n" +
            "5fc45ba79c9a2bfac2a19c5b\n" +
            "5fc45c239c9a2bfac2a19c5c\n" +
            "5fc45c439c9a2bfac2a19c5d\n" +
            "5fc45c919c9a2bfac2a19c5e\n" +
            "5fc45cd79c9a2bfac2a19c5f\n" +
            "5fc45d529c9a2bfac2a19c60\n" +
            "5fc45e619c9a2bfac2a19c61\n" +
            "5fc45eb39c9a2bfac2a19c62\n" +
            "5fc45f349c9a2bfac2a19c63\n" +
            "5fc461ad9c9a2bfac2a19c64\n" +
            "5fc4641a9c9a2bfac2a19c65\n" +
            "5fc464489c9a2bfac2a19c66\n" +
            "5fc466c39c9a2bfac2a19c67\n" +
            "5fc466c39c9a2bfac2a19c67\n" +
            "5fc46ace9c9a2bfac2a19c68\n" +
            "5fc476da9c9a2bfac2a19c69\n" +
            "5fc48ebd9c9a2bfac2a19c6a\n" +
            "5fc4a70a9c9a2bfac2a19c6d\n" +
            "5fc4a75f118778ee8cf1ee54\n" +
            "5fc4a7a69c9a2bfac2a19c6e\n" +
            "5fc4a8ba9c9a2bfac2a19c70\n" +
            "5fc4aa3a9c9a2bfac2a19c71\n" +
            "5fc4ac419c9a2bfac2a19c73\n" +
            "5fc4b03a9c9a2bfac2a19c74\n" +
            "5fc4b2929c9a2bfac2a19c75\n" +
            "5fc4b4039c9a2bfac2a19c76\n" +
            "5fc4b4fc9c9a2bfac2a19c77\n" +
            "5fc4b9319c9a2bfac2a19c79\n" +
            "5fc4bb5c9c9a2bfac2a19c7a\n" +
            "5fc4bd0f9c9a2bfac2a19c7b\n" +
            "5fc4bee09c9a2bfac2a19c7c\n" +
            "5fc4c0279c9a2bfac2a19c7d\n" +
            "5fc4c1aa9c9a2bfac2a19c7e\n" +
            "5fc4c4b69c9a2bfac2a19c7f\n" +
            "5fc4cf789c9a2bfac2a19c80\n" +
            "5fc4d2669c9a2bfac2a19c82\n" +
            "5fc5aa309c9a2bfac2a19c84\n" +
            "5fc5aaee118778ee8c1f3416\n" +
            "5fc5ab509c9a2bfac2a19c85\n" +
            "5fc5abf5118778ee8c1f66ec\n" +
            "5fc5ac679c9a2bfac2a19c86\n" +
            "5fc5ae6b9c9a2bfac2a19c87\n" +
            "5fc5af249c9a2bfac2a19c88\n" +
            "5fc5bb4005575fb712a1f9b9\n" +
            "5fc5bd8005575fb712a1f9bd\n" +
            "5fc5beaf05575fb712a1f9be\n" +
            "5fc5c0b905575fb712a1f9c0\n" +
            "5fc5c1f705575fb712a1f9c1\n" +
            "5fc5df1605575fb712a1f9c4\n" +
            "5fc5e1bc05575fb712a1f9c5\n" +
            "5fc5e2ee05575fb712a1f9c6\n" +
            "5fc5e4bf05575fb712a1f9c7\n" +
            "5fc5e6c305575fb712a1f9c9\n" +
            "5fc5ea9605575fb712a1f9cc\n" +
            "5fc5eb62118778ee8c2b4e5f\n" +
            "5fc5ebf705575fb712a1f9cd\n" +
            "5fc5ee6d05575fb712a1f9ce\n" +
            "5fc5ef7505575fb712a1f9cf\n" +
            "5fc5f26b05575fb712a1f9d0\n" +
            "5fc5f2e605575fb712a1f9d1\n" +
            "5fc5f35705575fb712a1f9d2\n" +
            "5fc5f45205575fb712a1f9d3\n" +
            "5fc6ff82a2efd7e2be363d90\n" +
            "5fc70149a2efd7e2be363d91\n" +
            "5fc70260a2efd7e2be363d92\n" +
            "5fc70555a2efd7e2be363d93\n" +
            "5fc70681a2efd7e2be363d95\n" +
            "5fc73d5ca2efd7e2be363da3\n" +
            "5fc73dde118778ee8c658d76\n" +
            "5fc73e19a2efd7e2be363da4\n" +
            "5fc73f4ea2efd7e2be363da6\n" +
            "5fc742eaa2efd7e2be363da8\n" +
            "5fc74395a2efd7e2be363da9\n" +
            "5fc745e1a2efd7e2be363daa\n" +
            "5fc745fea2efd7e2be363dab\n" +
            "5fc74622a2efd7e2be363dac\n" +
            "5fc83ee7a2efd7e2be363db8\n" +
            "5fc83f47a2efd7e2be363db9\n" +
            "5fc852bda2efd7e2be363dbf\n" +
            "5fc85386a2efd7e2be363dc1\n" +
            "5fc853dc118778ee8c94fb92\n" +
            "5fc853dd118778ee8c94fbad\n" +
            "5fc853f7a2efd7e2be363dc2\n" +
            "5fc85450a2efd7e2be363dc4\n" +
            "5fc88399634f9cc0021747fe\n" +
            "5fc8a1447f6d65954f0643d7\n" +
            "5fc996477f6d65954f0643e5\n" +
            "5fc998e97f6d65954f0643e6\n" +
            "5fcef1b2ed0195aa334e22f3\n" +
            "5fcef3c5ed0195aa334e22f5\n" +
            "5fcef710ed0195aa334e22f6\n" +
            "5fcef8c6ed0195aa334e22f8\n" +
            "5fcef998ed0195aa334e22f9\n" +
            "5fcefc56ed0195aa334e22fa\n" +
            "5fcefdafed0195aa334e22fb\n" +
            "5fd18379118778ee8ca946f0\n" +
            "5fd3213ed2504d8d6e122306\n" +
            "5fd33c8df965f19deb74c9a0\n" +
            "5fd735e98aa7dcf75d13bd40\n" +
            "5fd81c788aa7dcf75d13bd48\n" +
            "5fd81cb78aa7dcf75d13bd49\n" +
            "5fd81d2e8aa7dcf75d13bd4a\n" +
            "5fd81dfa8aa7dcf75d13bd4b\n" +
            "5fd81ee88aa7dcf75d13bd4d\n" +
            "5fd81f208aa7dcf75d13bd4e\n" +
            "5fd826158aa7dcf75d13bd50\n" +
            "5fd855028aa7dcf75d13bd52\n" +
            "5fd856908aa7dcf75d13bd53\n" +
            "5fd857c38aa7dcf75d13bd54\n" +
            "5fd858158aa7dcf75d13bd55\n" +
            "5fd859218aa7dcf75d13bd56\n" +
            "5fd859ef8aa7dcf75d13bd57\n" +
            "5fd85a5c8aa7dcf75d13bd58\n" +
            "5fd85ad08aa7dcf75d13bd59\n" +
            "5fd85b0f8aa7dcf75d13bd5a\n" +
            "5fd85c9b8aa7dcf75d13bd5b\n" +
            "5fd85e7d8aa7dcf75d13bd5c\n" +
            "5fd85eb68aa7dcf75d13bd5d\n" +
            "5fd85f8b8aa7dcf75d13bd5e\n" +
            "5fd860488aa7dcf75d13bd5f\n" +
            "5fd860b68aa7dcf75d13bd60\n" +
            "5fd861d98aa7dcf75d13bd61\n" +
            "5fd8622c8aa7dcf75d13bd62\n" +
            "5fd863de8aa7dcf75d13bd63\n" +
            "5fd867ed8aa7dcf75d13bd64\n" +
            "5feae8ba9c241db836e9ef7b\n" +
            "5ff5747f78b6d09e341d311b\n" +
            "5ff6d88fccd729e4d8492e4d\n" +
            "5ff6fc6575e4c9a272140b9a\n" +
            "5ffc06b65eb65949f43ebee2\n" +
            "5ffc0d0a5eb65949f43ebee5\n" +
            "5ffc0d325eb65949f43ebee6\n" +
            "60113b8efdb66904ce19c14a\n" +
            "60113b8efdb66904ce19c14b\n" +
            "60121ef1118778ee8c4a428d\n" +
            "60121efa118778ee8c4a44d7\n" +
            "60121f01118778ee8c4a469a\n" +
            "60125419f32be464219aeae6\n" +
            "60125419f32be464219aeae7\n" +
            "60125a25f32be464219aeae8\n" +
            "60125a25f32be464219aeae9\n" +
            "60125b99f32be464219aeaec\n" +
            "60125b99f32be464219aeaed\n" +
            "60125ff3f32be464219aeb18"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_layout_activity)
        ids.splitToSequence("\n").iterator().forEach {
            Log.v("ttaylor","conversation id=${it}")
        }

        /**
         * val and var
         */
        class ClassA(val list:List<String>){
            fun print(){
                list.print { it }.let { Log.v("ttaylor","val list=${it}") }
            }
        }

        var listA = mutableListOf<String>("a","b","c")
        val classA = ClassA(listA)
        classA.print()
        listA = mutableListOf("1","2","3")
        classA.print()


        class ClassB(var list:List<String>){
            fun print(){
                list.print { it }.let { Log.v("ttaylor","var list=${it}") }
            }
        }
        var listB = mutableListOf<String>("a","b","c")
        val classB = ClassB(listB)
        classB.print()
        listB = mutableListOf("1","2","3")
        classB.print()

        /**
         * sub string before and after
         */
        val string2 = "abcccdef"
        Log.v("ttaylor","sub string before = ${string2.substringBefore("ccc")} sub string after = ${string2.substringAfter("ccc")}")

        data class D1(var string: String) {
            fun copy() = D1(string)
        }

        data class D2(var strings: List<D1>)

        val data1 = listOf<Any>(
            D1("aaa"),
            D2(
                listOf(
                    D1("bbbb"),
                    D1("b"),
                    D1("a"),
                    D1("ccc")
                )
            )
        )

        val d2 = data1.getOrNull(1) as? D2 ?: return
        val target = d2.strings.find { it.string == "bbbb" } ?: return
        val index = d2.strings.indexOf(target)
        val newTarget = target.copy().apply { string = "changed bbbb"}
        val newTargetList = d2.strings.toMutableList().apply {
            set(index,newTarget)
        }
        d2.ofMap()?.print().let { Log.v("ttaylor","change list, old list = $it") }
        d2.strings = newTargetList
        d2.ofMap()?.print().let { Log.v("ttaylor","change list, new list = $it") }

        /**
         * change of list
         */
        val padWithChar = "12345".padStart(10, '.')
        Log.v("ttaylor", "tag=, KotlinActivity.onCreate()  padwithchar=${padWithChar}")

        val padend = "a".padEnd(10, '.')
        Log.v("ttaylor", "tag=, KotlinActivity.onCreate()  pad end=$padend")

        val list1 = listOf(
            "11",
            "22",
            "33",
            "44"
        )

        val list1ReadOnly = Collections.unmodifiableList(list1)
        val newList1 = list1ReadOnly.toMutableList()
        newList1[1] = "111"
        list1ReadOnly.print { it.toString() }.let { Log.v("ttaylor", " read only list=$it") }
        newList1.print { it.toString() }.let { Log.v("ttaylor", "new list = ${it}  ") }


        val readOnlyList1 = list1.toList()
        val mutableList1 = readOnlyList1.toMutableList()
        mutableList1[1] = "aaaa"
        readOnlyList1.print { it.toString() }.let { Log.v("ttaylor", " readOnlyList1=$it") }
        mutableList1.print { it.toString() }.let { Log.v("ttaylor", "mutableList1= ${it}  ") }

        /**
         * chunked
         */
        class String1(var list: List<String>)

        val ret = list1.chunked(2) {
            String1(listOf(*it.toTypedArray()))
        }

        ret.print { it.toString() }.let { Log.v("ttaylor", "chunked content=${it}") }


        btn3.setOnClickListener { Toast.makeText(this, "onclick for kotlin", Toast.LENGTH_LONG).show() }

        val oldList = listOf("1", "2", "3")
        val newList = oldList.toMutableList().apply {
            clear()
            add("11")
            add("22")
        }
        oldList.print { it.toString() }.let { Log.v("ttaylor", "old list=${it}  ") }
        newList.print { it.toString() }.let { Log.v("ttaylor", "new list=${it}  ") }

        /**
         * case: copy
         */
        data class ChinaPerson(var name: String, var age: Int)

        val cp1 = ChinaPerson("sb", 10)
        val cp2 = cp1.copy()
        cp1.age = 20
        Log.v("ttaylor", "tag=copy , KotlinActivity.onCreate()  cp2.age=${cp2.age}")


        listEquals()
        split()
        Log.v("ttaylor", "tag=sss, KotlinActivity.onCreate() isInToday=${isInToday(1567057536000)} [savedInstanceState]")
        val interface1 = object : Iinterface {
            override fun dod() {

            }
        }
        val interface2 = object : Iinterface {
            override fun dod() {
            }
        }
        val map = mutableMapOf(
            "1" to mutableListOf(interface1, interface2)
        )

        map.forEach { entry ->
            val iterator = entry.value.iterator()
            while (iterator.hasNext()) {
                val ite = iterator.next()
                if (ite == interface1) {
                    iterator.remove()
                }
            }
        }

        val str = "kfsklfjdklj$"
        str.substring(0, str.length - 1)
        Log.v("ttaylor", "tag=string, KotlinActivity.onCreate() str=${str.substring(0, str.length - 1)} ")


        /**
         * sequence case: early exit, better performance
         */
        sequenceOf("a", "b", "c")
            .map {
                print(it)
                it.toUpperCase()
            }
            .any {
                print(it)
                it.startsWith("B")
            }

        /**
         * sequence case: always put data-reducing operations befor data-transforming operations
         */
        //poor performance
        sequenceOf("a", "b", "c", "d")
            .map {
                println("map: $it")
                it.toUpperCase()
            }
            .filter {
                println("filter: $it")
                it.startsWith("a", ignoreCase = true)
            }
            .forEach {
                println("forEach: $it")
            }

        //better performance
        sequenceOf("a", "b", "c", "d")
            .filter {
                println("filter: $it")
                it.startsWith("a", ignoreCase = true)
            }
            .map {
                println("map: $it")
                it.toUpperCase()
            }
            .forEach {
                println("forEach: $it")
            }

        /**
         * sequence case: flapMap
         */
        val result = sequenceOf(listOf(1, 2, 3), listOf(4, 5, 6))
            .flatMap {
                it.asSequence().filter { it % 2 == 1 }
            }
            .toList()

        print(result)   // [1, 3, 5]

        /**
         * sequence case: withIndex()
         */
        val result2 = sequenceOf("a", "b", "c", "d")
            .withIndex()
            .filter { it.index % 2 == 0 }
            .map { it.value }
            .toList()

        print(result2)   // [a, c]

        /**
         * sequence case: joinToString()
         */
        data class Coordinate(var x: Int, var y: Int)
        data class Location(var country: String, var city: String, var coordinate: Coordinate)
        data class Person(var name: String, var age: Int, var locaton: Location? = null)


        Person("Peter", 16, Location("china", "shanghai", Coordinate(10, 20))).ofMap()?.print()
            .let { Log.v("ttaylor", "tag=343434, KotlinActivity.onCreate()  $it") }

        val persons = listOf(
            Person("Peter", 16),
            Person("Anna", 28),
            Person("Anna", 23),
            Person("Sonya", 39)
        )
        persons.print { it.ofMap()?.print() ?: "" }.let { Log.v("ttaylor", "tag=kfdksfjdlk, KotlinActivity.onCreate()  $it") }
        val result3 = persons
            .asSequence()
            .map { it.name }
            .distinct()
            .joinToString();

        print(result3)   // "Peter, Anna, Sonya"

        /**
         * lazy case:
         */
        val lazyValue: String by lazy {
            //this block will be invoked only once
            println("computed!")
            "Hello"
        }

        fun main() {
            println(lazyValue)
            println(lazyValue)
        }

        /**
         * Delegates.observable() case
         */
        var name: String by Delegates.observable("init") { property, old, new ->
            Log.v("ttaylor", "tag=observable, KotlinActivity.onCreate()  ${property.name} has been changed from $old to $new")
        }
        name = "taylor"

        /**
         * Delegates.vetoable() case
         */
        var time: String by Delegates.vetoable("13:34") { property, old, new ->
            !new.startsWith("2")
        }

        time = "11:22"
        Log.v("ttaylor", "tag=vetoable, KotlinActivity.onCreate()  time=$time")
        time = "22:22"
        Log.v("ttaylor", "tag=vetoable, KotlinActivity.onCreate()  time=$time")


        /**
         * create delegate as member function
         */
        class Delegate {
            operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
                return "$thisRef, thank you for delegating '${property.name}' to me!"
            }

            operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
                Log.v("ttaylor", "tag=, Delegate.setValue()  $value has been assigned to '${property.name}' in $thisRef.")
            }
        }

        class Example {
            var p: String by Delegate()
        }

        Example().p = "ddd"


        /**
         * create delegate as extension
         */
        class Delegate2 : ReadWriteProperty<Any?, String> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): String {
                return "aaa"
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
                Log.v("ttaylor", "tag=, Delegate2.setValue() $value has been assigned to ${property.name} ")
            }
        }

        var p: String by Delegate2()

        p = "bbb"
        Log.v("ttaylor", "tag=, KotlinActivity.onCreate()  ${p}")


        /**
         * create delegate as extension function
         */
        class Delegate3 {}

        operator fun Delegate3.getValue(thisRef: Any?, property: KProperty<*>): String {
            return "eee"
        }

        operator fun Delegate3.setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            Log.v("ttaylor", "tag=, KotlinActivity.setValue()  ${value} has assigned to ${property.name}")
        }

        var p2: String by Delegate3()

        p2 = "qqq"
        Log.v("ttaylor", "tag=, KotlinActivity.onCreate()  p2=${p2}")


        val objects = listOf("11", 1, "22")
        objects.filterIsInstance<String>().print { it }.also { Log.v("ttaylor", "tag=filterIsInstance, instance=${it} ") }


        /**
         * print map
         */
        val map2 = mutableMapOf(
            "12" to "dafdf",
            "23" to "eeee"
        )

        map2.print().let { Log.v("ttaylor", "tag=print map, KotlinActivity.onCreate()  ${it}") }


        /**
         * turn data class into map
         */
        val course = Course("computer", 50, true)
        course.ofMap()?.print().let { Log.v("ttaylor", "tag=data map, KotlinActivity.onCreate()  ${it}") }

        /**
         * invoke 约定
         */
        Invoke()
        Invoke2()

        /**
         * first day of week
         */
        Log.v("ttaylor", "tag=date, KotlinActivity.onCreate()  first day of week=${thisMondayInMillis()}, end day of week=${thisSundayInMillis()}")

        val charSequence = "零一二三四五六30002二三"
        Log.v("ttaylor", "tag=subdigit, KotlinActivity.onCreate()  digit=${charSequence.subDigit}")

        charSequence.splitByDigit().forEach {
            Log.v("ttaylor", "tag=split by digit, KotlinActivity.onCreate()  c=${it}")
        }
        textCompanionObjec.sec
        textCompanionObjec.sec2
        textCompanionObjec.sec3
        textCompanionObjec.CC.doB()
        textCompanionObjec.doA()


        /**
         * spread array to listOf()
         */
        val otherList = mutableListOf<String>().apply {
            add("bbbb")
            add("ccc")
        }
        val list = listOf<Any>(
            "aaa",
            *otherList.toTypedArray()
        )
        val list2 = listOf<Any>(
            "aaa",
            otherList
        )
        list.print { it.toString() }?.let { Log.v("ttaylor", "tag=spread array, list=$it") }
        list2.print { it.toString() }?.let { Log.v("ttaylor", "tag=spread array failed, list=$it") }
    }

    private fun split() {
        val str = "asb;ddd;"
        Log.v("ttaylor", "tag=split, KotlinActivity.split()  split=${str.split(";")}")
    }

    private fun listEquals() {
        Log.v("ttaylor", "tag=equals, KotlinActivity.listEquals() isEquals=${list1 == list2}")
    }

    fun isInToday(timestamp: Long): Boolean {

        val beginningOfToday = Calendar.getInstance().let {
            it.set(Calendar.HOUR_OF_DAY, 0)
            it.set(Calendar.MINUTE, 0)
            it.set(Calendar.SECOND, 0)
            it.set(Calendar.MILLISECOND, 0)
            it.timeInMillis
        }

        val endingOfToday = Calendar.getInstance().let {
            it.set(Calendar.HOUR_OF_DAY, 23)
            it.set(Calendar.MINUTE, 59)
            it.set(Calendar.SECOND, 59)
            it.set(Calendar.MILLISECOND, 999)
            it.timeInMillis
        }

        return timestamp in beginningOfToday..endingOfToday

    }

    fun thisMondayInMillis() = Calendar.getInstance().let { c ->
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) c.add(Calendar.DATE, -1)
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        c.timeInMillis
    }

    fun thisSundayInMillis() = Calendar.getInstance().let { c ->
        if (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
            c.add(Calendar.DATE, 1)
        }
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        c.timeInMillis
    }

    interface Iinterface {
        fun dod()
    }
}

class textCompanionObjec {
    companion object {
        val sec = 1

        @JvmStatic
        val sec2 = 2

        @JvmField
        val sec3 = 3
        fun doA() {}
    }

    object CC {
        fun doB() {}
    }
}

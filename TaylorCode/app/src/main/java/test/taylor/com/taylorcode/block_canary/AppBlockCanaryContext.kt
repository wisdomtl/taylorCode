package test.taylor.com.taylorcode.block_canary

import com.github.moduth.blockcanary.BlockCanaryContext

class AppBlockCanaryContext: BlockCanaryContext() {

    override fun provideQualifier(): String {
        return "taylor code"
    }

    override fun providePath(): String {
        return "/blcokcanary/"
    }

    override fun displayNotification(): Boolean {
        return true
    }

    override fun provideBlockThreshold(): Int {
        return 50
    }
}
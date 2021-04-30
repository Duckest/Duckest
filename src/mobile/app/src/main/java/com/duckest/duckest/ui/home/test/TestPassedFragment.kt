package com.duckest.duckest.ui.home.test

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.duckest.duckest.BuildConfig.APPLICATION_ID
import com.duckest.duckest.R
import com.duckest.duckest.util.Utils
import com.duckest.duckest.databinding.FragmentTestPassedBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class TestPassedFragment : Fragment() {
    private lateinit var binding: FragmentTestPassedBinding
    private lateinit var args: TestPassedFragmentArgs
    private lateinit var bitmapCertificate: Bitmap
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        args = TestPassedFragmentArgs.fromBundle(requireArguments())
        (activity as AppCompatActivity).supportActionBar?.title = getString(args.testId)
        binding = FragmentTestPassedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wrongAnswers.text =
            getString(R.string.test_result_wrong_answers, args.total - args.correct)
        binding.result.text =
            getString(R.string.test_result_right_answers, args.correct, args.total)
        bitmapCertificate = Utils.drawTextToBitmap(
            requireContext(),
            R.mipmap.img_certificate,
            text1 = "Имя Фамилия",
            text2 = "${getString(args.testId)} ${getString(args.levelId)}"
        )
        binding.certificate.setOnClickListener {
            saveMediaToStorage()
            Toast.makeText(
                requireContext(),
                getString(R.string.certificate_saved_information),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getBitmapFromView(bmp: Bitmap?): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(requireContext().cacheDir, getString(R.string.certificate_passed_name))
            val out = FileOutputStream(file)
            bmp?.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.close()
            bmpUri = FileProvider.getUriForFile(
                requireContext(),
                "$APPLICATION_ID.fileProvider",
                file
            )

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.test_passed_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> share()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveMediaToStorage() {
        val filename = "${getString(args.testId)}_${getString(args.levelId)}.jpg"
        var fos: OutputStream? = null
        val resolver = requireContext().contentResolver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            resolver?.also { res ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    res.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmapCertificate.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun share() {
        val uri = getBitmapFromView(bitmapCertificate)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            putExtra(Intent.EXTRA_STREAM, uri)
        }
        val chooser = Intent.createChooser(
            shareIntent,
            getString(R.string.share)
        )
        val resInfoList: List<ResolveInfo> = requireContext().packageManager.queryIntentActivities(
            chooser,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        for (resolveInfo in resInfoList) {
            val packageName: String = resolveInfo.activityInfo.packageName
            requireContext().grantUriPermission(
                packageName,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
        startActivity(
            chooser
        )
    }
}
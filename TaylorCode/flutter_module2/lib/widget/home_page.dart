import 'package:flutter/material.dart';

import 'drop_down_menu.dart';
import 'easy_popup.dart';
import 'guide_pop_up.dart';

void main() {
  runApp(const MyApp2());
}

class MyApp2 extends StatelessWidget {
  const MyApp2({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or press Run > Flutter Hot Reload in a Flutter IDE). Notice that the
        // counter didn't reset back to zero; the application is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: Home(),
    );
  }
}

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  GlobalKey key1 = new GlobalKey(), key2 = new GlobalKey(), key3 = new GlobalKey(), key4 = new GlobalKey();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(50),
        child: AppBar(
          title: Text('EasyPopup'),
        ),
      ),
      backgroundColor: Colors.white,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            GestureDetector(
              key: key1,
              behavior: HitTestBehavior.opaque,
              onTap: _showDropDownMenu,
              child: Container(
                color: Colors.blueAccent,
                alignment: Alignment.center,
                width: 200,
                height: 50,
                child: Text(
                  'ShowDropDownMenu',
                  style: TextStyle(color: Colors.white),
                ),
              ),
            ),
            GestureDetector(
              key: key2,
              behavior: HitTestBehavior.opaque,
              onTap: _showGuidePopup,
              child: Container(
                width: 200,
                height: 50,
                color: Colors.blueAccent,
                alignment: Alignment.center,
                child: Text(
                  'ShowGuidePopup',
                  style: TextStyle(color: Colors.white),
                ),
              ),
            ),
            GestureDetector(
              key: key3,
              behavior: HitTestBehavior.opaque,
              onTap: _showMultiHighlightsGuidePopup,
              child: Container(
                width: 200,
                height: 50,
                color: Colors.blueAccent,
                alignment: Alignment.center,
                child: Text(
                  'ShowMultiHighlightPopup',
                  style: TextStyle(color: Colors.white),
                ),
              ),
            ),
            GestureDetector(
              key: key4,
              behavior: HitTestBehavior.opaque,
              onTap: _showLoading,
              child: Container(
                color: Colors.blueAccent,
                alignment: Alignment.center,
                width: 200,
                height: 50,
                child: Text(
                  'ShowLoading',
                  style: TextStyle(color: Colors.white),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  _showDropDownMenu() {
    EasyPopup.show(context, DropDownMenu(), offsetLT: Offset(0, MediaQuery.of(context).padding.top + 50));
  }

  _showLoading() {
    // EasyPopup.show(context, Loading(),
    //     darkEnable: false, duration: Duration(milliseconds: 0));
  }

  _showGuidePopup() {
    // RenderBox? box = key1.currentContext?.findRenderObject() as RenderBox?;
    // Offset offset = box?.localToGlobal(Offset.zero) ?? const Offset(0, 0);
    // double left = offset.dx - 5;
    // double top = offset.dy - 5;
    // double width = (box?.size.width ?? 0) + 10;
    // double height = (box?.size.height ?? 0) + 10;
    // List<RRect> highlights = [
    //   RRect.fromRectAndRadius(
    //     Rect.fromLTWH(left, top, width, height),
    //     const Radius.circular(10),
    //   ),
    // ];
    EasyPopup.show(
      context,
      GuidePopup([key1]),
      cancelable: false,
      duration: const Duration(milliseconds: 100),
    );
  }

  _showMultiHighlightsGuidePopup() {
    List<GlobalKey> keys = [key1, key2, key3, key4];
    List<RRect> highlights = [];
    for (GlobalKey key in keys) {
      RenderBox? box = key.currentContext?.findRenderObject() as RenderBox?;
      Offset offset = box?.localToGlobal(Offset.zero) ?? const Offset(0, 0);
      double left = offset.dx - 5;
      double top = offset.dy - 5;
      double width = (box?.size.width ?? 0) + 10;
      double height = (box?.size.height ?? 0) + 10;
      highlights.add(
        RRect.fromRectAndRadius(
          Rect.fromLTWH(left, top, width, height),
          Radius.circular(10),
        ),
      );
    }
    EasyPopup.show(
      context,
      GuidePopup([key1]),
      cancelable: false,
      highlights: highlights,
      duration: Duration(milliseconds: 100),
    );
  }
}

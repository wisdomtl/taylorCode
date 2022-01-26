import 'package:flutter/material.dart';

import 'easy_popup.dart';
import 'easy_popup_child.dart';

class GuidePopup extends StatefulWidget with EasyPopupChild {
  final List<GlobalKey> highlightKeys;

  GuidePopup(this.highlightKeys);

  @override
  _GuidePopupState createState() => _GuidePopupState();

  @override
  dismiss() {}
}

class _GuidePopupState extends State<GuidePopup> with SingleTickerProviderStateMixin {
  int highlightIndex = -1;
  late double hLeft, hTop, hWidth, hHeight, hRadius;
  Animation<Offset>? _animation;
  AnimationController? _controller;

  @override
  void initState() {
    super.initState();
    Rect? rect = _calculateNextGuide() ?? const Rect.fromLTRB(0, 0, 0, 0);
    hRadius = 10;
    hLeft = rect.left;
    hTop = rect.top;
    hHeight = rect.height;
    hWidth = rect.width;

    _controller = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 300),
    );
    _animation = Tween<Offset>(begin: const Offset(0, 0), end: const Offset(0, 1)).animate(_controller!);
    _controller?.forward();
  }

  dismiss() {
    _controller?.reverse();
  }

  @override
  void dispose() {
    super.dispose();
    _controller?.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      behavior: HitTestBehavior.opaque,
      onTap: () => _next(),
      child: Scaffold(
        backgroundColor: const Color(0x330C0B1C),
        body: Container(
          padding: EdgeInsets.only(
            top: hTop + 50,
            left: hLeft + (hWidth - 300) / 2,
          ),
          child: SlideTransition(
            position: _animation!,
            child: Container(width: 300, height: 50, alignment: Alignment.center, color: Colors.white, child: Text('${_getShowText()}')),
          ),
        ),
      ),
    );
  }

  String _getShowText() {
    switch (highlightIndex) {
      case 0:
        return 'Click here to show dropdown menu.';
      case 1:
        return 'Click here to show guide.';
      case 2:
        return 'Click here to show multi highlight guide.';
      case 3:
        return 'Click here to show loading.';
    }
    return '';
  }

  _next() {
    Rect? rect = _calculateNextGuide();
    if (rect == null) {
      return;
    }
    List<RRect> highlights = [
      RRect.fromRectAndRadius(
        rect,
        Radius.circular(hRadius),
      ),
    ];
    setState(() {
      EasyPopup.setHighlights(context, highlights);
      hLeft = rect.left;
      hTop = rect.top;
      hHeight = rect.height;
      hWidth = rect.width;
    });
  }

  Rect? _calculateNextGuide() {
    highlightIndex++;
    if (highlightIndex >= widget.highlightKeys.length) {
      _dismiss();
      return null;
    }
    RenderBox? box = widget.highlightKeys[highlightIndex].currentContext?.findRenderObject() as RenderBox?;
    Offset offset = box?.localToGlobal(Offset.zero) ?? const Offset(0, 0);
    return Rect.fromLTWH(
      offset.dx - 5,
      offset.dy - 5,
      (box?.size.width ?? 0) + 10,
      (box?.size.height ?? 0) + 10,
    );
  }

  _dismiss() {
    EasyPopup.pop(context);
  }
}
